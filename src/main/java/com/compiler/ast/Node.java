package main.java.com.compiler.ast;

import java.util.ArrayList;
import main.java.com.compiler.lexer.Lexer;
import java.util.List;

public class Node {
    public String type;
    public String resolvedType;
    public int line = Lexer.tokLine;
    public int column = Lexer.tokColumn;
    public List<Node> children = new ArrayList<>();

    public Node(String type) {
        this.type = type;
    }

    public void addChild(Node child) {
        if (child != null) children.add(child);
    }

    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    public void print(String indent) {
        System.out.println(indent + type);
        for (Node child : children) {
            if (child != null) {
                child.print(indent + "  ");
            }
        }
    }

    public int firstLine() {
        int best = line;
        for (Node c : children) {
            if (c != null) best = Math.min(best, c.firstLine());
        }
        return best;
    }
}
