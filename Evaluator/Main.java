package Evaluator;
import java.nio.file.*;
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) { System.out.println("Usage: java evaluator.Main <file>"); return; }
        String code = new String(Files.readAllBytes(Paths.get(args[0])));
        new Interpreter().run(code);
    }
}
