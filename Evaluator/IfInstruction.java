package Evaluator;
import java.util.List;
import parser.Expression;

public class IfInstruction implements Instruction {
    private final Expression cond;
    private final List<Instruction> body;
    public IfInstruction(Expression c, List<Instruction> b) { this.cond = c; this.body = b; }
    @Override public void execute(Environment env) {
        if (Boolean.TRUE.equals(cond.evaluate(env))) { for (Instruction i : body) i.execute(env); }
    }
}

