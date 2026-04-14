import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int cur = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();
        while (!isAtEnd()) {
            if (match(TokenType.NEWLINE)) continue;
            instructions.add(parseInstruction());
        }
        return instructions;
    }

    private Instruction parseInstruction() {
        if (match(TokenType.SET)) {
            String name = consume(TokenType.IDENTIFIER, "Expect variable name").getValue();
            consume(TokenType.EQUAL, "Expect '=' after variable name");
            Expression expr = parseExpression();
            return new AssignInstruction(name, expr);
        } else if (match(TokenType.SHOW)) {
            Expression expr = parseExpression();
            return new PrintInstruction(expr);
        } else if (match(TokenType.WHEN)) {
            Expression condition = parseComparison();
            consume(TokenType.COLON, "Expect ':' after condition");
            consume(TokenType.NEWLINE, "Expect newline after ':'");
            List<Instruction> body = parseBlock();
            return new IfInstruction(condition, body);
        } else if (match(TokenType.LOOP)) {
            int count = (int) Double.parseDouble(consume(TokenType.NUMBER, "Expect repeat count").getValue());
            consume(TokenType.COLON, "Expect ':' after loop count");
            consume(TokenType.NEWLINE, "Expect newline after ':'");
            List<Instruction> body = parseBlock();
            return new RepeatInstruction(count, body);
        }
        throw new RuntimeException("Unexpected token: " + peek().getValue() + " at line " + peek().getLine());
    }

     private List<Instruction> parseBlock() {
        List<Instruction> body = new ArrayList<>();
        while (!isAtEnd() && !check(TokenType.END)) {
            if (match(TokenType.NEWLINE)) continue;
            body.add(parseInstruction());
        }
        consume(TokenType.END, "Expect 'end' at the end of block");
        return body;
    }

    private Expression parseComparison() {
        Expression expr = parseExpression();
        while (match(TokenType.GREATER, TokenType.LESS, TokenType.EQUAL_EQUAL)) {
            String operator = prev().getValue();
            Expression right = parseExpression();
            expr = new BinaryOpNode(expr, operator, right);
        }
        return expr;
    }

    private Expression parseExpression() {
        Expression expr = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            String operator = prev().getValue();
            Expression right = parseTerm();
            expr = new BinaryOpNode(expr, operator, right);
        }
        return expr;
    }

    private Expression parseTerm() {
        Expression expr = parsePrimary();
        while (match(TokenType.STAR, TokenType.SLASH)) {
            String operator = prev().getValue();
            Expression right = parsePrimary();
            expr = new BinaryOpNode(expr, operator, right);
        }
        return expr;
    }

    private Expression parsePrimary() {
        if (match(TokenType.NUMBER)) {
            return new NumberNode(Double.parseDouble(prev().getValue()));
        } else if (match(TokenType.STRING)) {
            return new StringNode(prev().getValue());
        } else if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(prev().getValue());
        }
        throw new RuntimeException("Expect expression at line " + peek().getLine());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message + " at line " + peek().getLine());
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) cur++;
        return prev();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(cur);
    }

    private Token prev() {
        return tokens.get(cur - 1);
    }
}
