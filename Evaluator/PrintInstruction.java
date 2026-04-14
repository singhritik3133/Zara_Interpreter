package Evaluator;
import parser.Expression;

public class PrintInstruction implements Instruction {
    private final Expression expr;
    public PrintInstruction(Expression e) { this.expr = e; }
    @Override public void execute(Environment env) {
        Object v = expr.evaluate(env);
        if (v instanceof Double) {
            double d = (Double)v;
            System.out.println(d == Math.floor(d) ? (long)d : d);
        } else System.out.println(v);
    }
}

