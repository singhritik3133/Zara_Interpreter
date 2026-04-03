import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    // ------------------------------------------------------------------ top-level

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();
        skipNewlines();
        while (!isAtEnd()) {
            Instruction instr = parseInstruction();
            if (instr != null) instructions.add(instr);
            skipNewlines();
        }
        return instructions;
    }

    // ------------------------------------------------------------------ instructions

    private Instruction parseInstruction() {
        Token token = peek();

        switch (token.getType()) {
            case SET:  return parseSet();
            case SHOW: return parseShow();
            case WHEN: return parseWhen();
            case LOOP: return parseLoop();
            default:
                throw new RuntimeException(
                    "Unexpected token '" + token.getValue() + "' on line " + token.getLine());
        }
    }

    /**
     * set <identifier> = <expression>
     */
    private Instruction parseSet() {
        consume(TokenType.SET);
        Token nameToken = consume(TokenType.IDENTIFIER);
        consume(TokenType.EQUAL);
        Expression expr = parseExpression();
        expectNewlineOrEOF();
        return new AssignInstruction(nameToken.getValue(), expr);
    }

    /**
     * show <expression>
     */
    private Instruction parseShow() {
        consume(TokenType.SHOW);
        Expression expr = parseExpression();
        expectNewlineOrEOF();
        return new PrintInstruction(expr);
    }

    /**
     * when <condition>:
     *     <body>
     *
     * Body ends when indentation returns to top level (i.e. next token is SET/SHOW/WHEN/LOOP or EOF).
     * We detect body end by looking for top-level keywords after newlines.
     */
    private Instruction parseWhen() {
        consume(TokenType.WHEN);
        Expression condition = parseComparison();
        consume(TokenType.COLON);
        expectNewlineOrEOF();
        List<Instruction> body = parseBlock();
        return new IfInstruction(condition, body);
    }

    /**
     * loop <number>:
     *     <body>
     */
    private Instruction parseLoop() {
        consume(TokenType.LOOP);
        Token countToken = consume(TokenType.NUMBER);
        int count = (int) Double.parseDouble(countToken.getValue());
        consume(TokenType.COLON);
        expectNewlineOrEOF();
        List<Instruction> body = parseBlock();
        return new RepeatInstruction(count, body);
    }

    /**
     * A block is a sequence of instructions that belong to the body of a when/loop.
     * We stop when we see a top-level keyword at column 1 or EOF.
     */
    private List<Instruction> parseBlock() {
        List<Instruction> body = new ArrayList<>();
        skipNewlines();
        while (!isAtEnd() && !isTopLevelKeyword(peek())) {
            body.add(parseInstruction());
            skipNewlines();
        }
        return body;
    }

    // ------------------------------------------------------------------ expressions

    /**
     * Entry point for expression parsing.
     * Also handles comparison operators at this level so they work inside when conditions.
     */
    private Expression parseExpression() {
        Expression left = parseTerm();

        // Handle + and -
        while (check(TokenType.PLUS) || check(TokenType.MINUS)) {
            String op = advance().getValue();
            Expression right = parseTerm();
            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    /**
     * Parses a comparison: <expression> > <expression>, etc.
     * Used specifically for condition parsing in when.
     */
    private Expression parseComparison() {
        Expression left = parseExpression();

        if (check(TokenType.GREATER) || check(TokenType.LESS) || check(TokenType.EQUAL_EQUAL)) {
            String op = advance().getValue();
            Expression right = parseExpression();
            return new BinaryOpNode(left, op, right);
        }

        return left;
    }

    /** Handles * and / */
    private Expression parseTerm() {
        Expression left = parsePrimary();

        while (check(TokenType.STAR) || check(TokenType.SLASH)) {
            String op = advance().getValue();
            Expression right = parsePrimary();
            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    /** Handles a single number, string, variable, or parenthesised expression */
    private Expression parsePrimary() {
        Token token = peek();

        if (token.getType() == TokenType.NUMBER) {
            advance();
            return new NumberNode(Double.parseDouble(token.getValue()));
        }

        if (token.getType() == TokenType.STRING) {
            advance();
            return new StringNode(token.getValue());
        }

        if (token.getType() == TokenType.IDENTIFIER) {
            advance();
            return new VariableNode(token.getValue());
        }

        throw new RuntimeException(
            "Expected a number, string, or variable but found '"
            + token.getValue() + "' on line " + token.getLine());
    }

    // ------------------------------------------------------------------ helpers

    private boolean isTopLevelKeyword(Token t) {
        TokenType type = t.getType();
        return type == TokenType.SET
            || type == TokenType.SHOW
            || type == TokenType.WHEN
            || type == TokenType.LOOP;
    }

    private void skipNewlines() {
        while (check(TokenType.NEWLINE)) advance();
    }

    private void expectNewlineOrEOF() {
        if (check(TokenType.NEWLINE) || check(TokenType.EOF)) {
            if (check(TokenType.NEWLINE)) advance();
        }
        // If neither, we just continue (tolerate missing newline at end of file)
    }

    private Token consume(TokenType expected) {
        Token token = peek();
        if (token.getType() != expected) {
            throw new RuntimeException(
                "Expected " + expected + " but found '"
                + token.getValue() + "' on line " + token.getLine());
        }
        return advance();
    }

    private Token advance() {
        Token t = tokens.get(current);
        current++;
        return t;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private boolean check(TokenType type) {
        return !isAtEnd() && peek().getType() == type;
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }
}
