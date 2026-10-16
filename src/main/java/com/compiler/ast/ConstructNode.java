package main.java.com.compiler.ast;

import java.util.List;

public class ConstructNode extends Node {
    public List<Node> parameters;
    public List<Node> statements;

    public ConstructNode(List<Node> parameters, List<Node> statements) {
        super("ConstructorDeclaration");
        this.parameters = parameters;
        this.statements = statements;

        if (parameters != null) {
            for (Node p : parameters) {
                addChild(p);
            }
        }
        if (statements != null) {
            for (Node s : statements) {
                addChild(s);
            }
        }
    }
}