package Evaluator;
import java.util.List;
import parser.Expression;

public class RepeatInstruction implements Instruction {
    private final int count;
    private final List<Instruction> body;
    public RepeatInstruction(int c, List<Instruction> b) { this.count = c; this.body = b; }
    @Override public void execute(Environment env) {
        for (int x = 0; x < count; x++) { for (Instruction i : body) i.execute(env); }
    }
}

