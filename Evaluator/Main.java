import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main <source-file.zara>");
            System.exit(1);
        }

        String filePath = args[0];
        String sourceCode;

        try {
            sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file '" + filePath + "': " + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            new Interpreter().run(sourceCode);
        } catch (RuntimeException e) {
            System.err.println("Runtime error: " + e.getMessage());
            System.exit(1);
        }
    }
}