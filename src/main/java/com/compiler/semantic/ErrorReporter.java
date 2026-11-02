package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.Node;
import java.util.ArrayList;
import java.util.List;

public class ErrorReporter {
    private final List<String> errors = new ArrayList<>();

    public void error(Node at, String message) {
        String pos = (at != null) ? String.valueOf(at.firstLine()) : "?";
        errors.add("Semantic error at line " + pos + ": " + message);
    }

    public boolean hasErrors() { return !errors.isEmpty(); }

    public void printAll() {
        for (String e : errors) System.err.println(e);
    }
}