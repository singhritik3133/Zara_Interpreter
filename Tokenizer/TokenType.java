public enum TokenType {
    // Keywords
    SET,        // set
    SHOW,       // show
    WHEN,       // when
    LOOP,       // loop

    // Literals
    NUMBER,     // e.g. 10, 3.14
    STRING,     // e.g. "hello"
    IDENTIFIER, // variable names

    // Arithmetic Operators
    PLUS,       // +
    MINUS,      // -
    STAR,       // *
    SLASH,      // /

    // Comparison Operators
    GREATER,    // >
    LESS,       // <
    EQUAL_EQUAL,// ==

    // Other Symbols
    EQUAL,      // =
    COLON,      // :

    // Structure
    NEWLINE,
    EOF
}