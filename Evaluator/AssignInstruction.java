package Evaluator;
import parser.Expression;

public class AssignInstruction implements Instruction {
    private final String name;
    private final Expression expr;
    public AssignInstruction(String n, Expression e) { this.name = n; this.expr = e; }
    @Override public void execute(Environment env) { env.set(name, expr.evaluate(env)); }
}
