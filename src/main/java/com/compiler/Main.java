package main.java.com.compiler;

import main.java.com.compiler.ast.Node;
import main.java.com.compiler.lexer.Lexer;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <source_file.o>");
            return;
        }

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(args[0])));
            Lexer lexer = new Lexer(sourceCode);
            Parser parser = new Parser(lexer);

            System.out.println("--- Starting Syntax Analysis ---");

            // 3. Запускаем парсинг
            if (parser.parse()) {
                System.out.println("\n--- Abstract Syntax Tree (AST) ---");
                // Забираем корень дерева, который сохранил Саня, и печатаем его
                Node root = parser.getRootNode();
                if (root != null) {
                    root.print("");
                } else {
                    System.out.println("Root node is null.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error during compilation: " + e.getMessage());
        }
    }
}
