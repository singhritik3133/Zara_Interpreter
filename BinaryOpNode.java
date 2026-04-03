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

        // Comparison operators — return Boolean
        if (operator.equals(">")) {
            double l = toDouble(leftVal);
            double r = toDouble(rightVal);
            return l > r;
        }
        if (operator.equals("<")) {
            double l = toDouble(leftVal);
            double r = toDouble(rightVal);
            return l < r;
        }
        if (operator.equals("==")) {
            if (leftVal instanceof Double && rightVal instanceof Double) {
                return leftVal.equals(rightVal);
            }
            return leftVal.toString().equals(rightVal.toString());
        }

        // Arithmetic operators — return Double
        double l = toDouble(leftVal);
        double r = toDouble(rightVal);
        switch (operator) {
            case "+": return l + r;
            case "-": return l - r;
            case "*": return l * r;
            case "/":
                if (r == 0) throw new RuntimeException("Division by zero.");
                return l / r;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }

    private double toDouble(Object val) {
        if (val instanceof Double) return (Double) val;
        throw new RuntimeException("Expected a number but got: " + val);
    }
}