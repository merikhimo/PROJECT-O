package main.java.com.compiler;

import main.java.com.compiler.ast.Node;
import main.java.com.compiler.lexer.Lexer;
import main.java.com.compiler.semantic.*;

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

            if (parser.parse()) {
                System.out.println("\n--- Abstract Syntax Tree (Before optimisation) ---");
                Node root = parser.getRootNode();
                if (root != null) {
                    root.print("");

                    System.out.println("\n--- Syntax Analysis ---");
                    ErrorReporter errors = new ErrorReporter();
                    SymbolTable table = new SymbolTable();
                    new DeclarationCollector(table, errors).collect(root);
                    if (!errors.hasErrors()) {
                        new TypeChecker(table, errors).check(root);
                    }
                    if (errors.hasErrors()) {
                        errors.printAll();
                        return;
                    }
                    System.out.println("Semantic analysis: OK");


                    Optimizer opt = new Optimizer();
                    do {
                        root = opt.run(root);
                    } while (opt.changed);

                    System.out.println("\n--- AST after optimization ---");
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
