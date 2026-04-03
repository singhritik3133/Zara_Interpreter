import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String source;
    private int pos;
    private int line;

    public Tokenizer(String source) {
        this.source = source;
        this.pos = 0;
        this.line = 1;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < source.length()) {
            char c = source.charAt(pos);

            // Skip spaces and tabs (but NOT newlines)
            if (c == ' ' || c == '\t' || c == '\r') {
                pos++;
                continue;
            }

            // Newlines
            if (c == '\n') {
                tokens.add(new Token(TokenType.NEWLINE, "\\n", line));
                line++;
                pos++;
                continue;
            }

            // Comments — lines starting with # are ignored
            if (c == '#') {
                while (pos < source.length() && source.charAt(pos) != '\n') {
                    pos++;
                }
                continue;
            }

            // String literals
            if (c == '"') {
                tokens.add(readString());
                continue;
            }

            // Numbers
            if (Character.isDigit(c)) {
                tokens.add(readNumber());
                continue;
            }

            // Identifiers and keywords
            if (Character.isLetter(c) || c == '_') {
                tokens.add(readIdentifierOrKeyword());
                continue;
            }

            // Operators and symbols
            switch (c) {
                case '+': tokens.add(new Token(TokenType.PLUS,    "+", line)); pos++; break;
                case '-': tokens.add(new Token(TokenType.MINUS,   "-", line)); pos++; break;
                case '*': tokens.add(new Token(TokenType.STAR,    "*", line)); pos++; break;
                case '/': tokens.add(new Token(TokenType.SLASH,   "/", line)); pos++; break;
                case '>': tokens.add(new Token(TokenType.GREATER, ">", line)); pos++; break;
                case '<': tokens.add(new Token(TokenType.LESS,    "<", line)); pos++; break;
                case ':': tokens.add(new Token(TokenType.COLON,   ":", line)); pos++; break;
                case '=':
                    if (pos + 1 < source.length() && source.charAt(pos + 1) == '=') {
                        tokens.add(new Token(TokenType.EQUAL_EQUAL, "==", line));
                        pos += 2;
                    } else {
                        tokens.add(new Token(TokenType.EQUAL, "=", line));
                        pos++;
                    }
                    break;
                default:
                    throw new RuntimeException("Unexpected character '" + c + "' on line " + line);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    private Token readString() {
        int startLine = line;
        pos++; // skip opening "
        StringBuilder sb = new StringBuilder();
        while (pos < source.length() && source.charAt(pos) != '"') {
            sb.append(source.charAt(pos));
            pos++;
        }
        if (pos >= source.length()) {
            throw new RuntimeException("Unterminated string starting on line " + startLine);
        }
        pos++; // skip closing "
        return new Token(TokenType.STRING, sb.toString(), startLine);
    }

    private Token readNumber() {
        int startLine = line;
        StringBuilder sb = new StringBuilder();
        while (pos < source.length() && (Character.isDigit(source.charAt(pos)) || source.charAt(pos) == '.')) {
            sb.append(source.charAt(pos));
            pos++;
        }
        return new Token(TokenType.NUMBER, sb.toString(), startLine);
    }

    private Token readIdentifierOrKeyword() {
        int startLine = line;
        StringBuilder sb = new StringBuilder();
        while (pos < source.length() && (Character.isLetterOrDigit(source.charAt(pos)) || source.charAt(pos) == '_')) {
            sb.append(source.charAt(pos));
            pos++;
        }
        String word = sb.toString();

        switch (word) {
            case "set":  return new Token(TokenType.SET,  word, startLine);
            case "show": return new Token(TokenType.SHOW, word, startLine);
            case "when": return new Token(TokenType.WHEN, word, startLine);
            case "loop": return new Token(TokenType.LOOP, word, startLine);
            default:     return new Token(TokenType.IDENTIFIER, word, startLine);
        }
    }
}