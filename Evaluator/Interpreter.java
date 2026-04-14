package Evaluator;
import java.util.List;
import Tokenizer.*;
import parser.*;
public class Interpreter {
    public void run(String code) {
        List<Token> tokens = new Tokenizer(code).tokenize();
        List<Instruction> instructions = new Parser(tokens).parse();
        Environment env = new Environment();
        for (Instruction i : instructions) i.execute(env);
    }
}
