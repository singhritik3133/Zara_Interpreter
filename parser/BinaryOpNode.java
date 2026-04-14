public class BinaryOpNode implements Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public BinaryOpNode(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = left.evaluate(env);
        Object rightVal = right.evaluate(env);

        if (leftVal instanceof Double && rightVal instanceof Double) {
            double l = (Double) leftVal;
            double r = (Double) rightVal;
            switch (operator) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return l / r;
                case ">": return l > r;
                case "<": return l < r;
                case "==": return l == r;
            }
        } else if (leftVal instanceof String || rightVal instanceof String) {
            // String concatenation support (optional but useful)
            if (operator.equals("+")) {
                return String.valueOf(leftVal) + String.valueOf(rightVal);
            }
            if (operator.equals("==")) {
                return String.valueOf(leftVal).equals(String.valueOf(rightVal));
            }
        }

        throw new RuntimeException("Invalid operation: " + leftVal + " " + operator + " " + rightVal);
    }
}
