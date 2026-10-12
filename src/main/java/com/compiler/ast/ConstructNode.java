package main.java.com.compiler.ast;

import java.util.List;

class ConstructNode extends Node {
    public ConstructNode(List<Node> parameters, List<Node> statements) {
        super("ConstructorDeclaration");
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