package main.java.com.compiler.ast;

import java.util.List;

class MethodBodyNode extends Node {
    public MethodBodyNode(List<Node> statements) {
        super("MethodBody");
        if (statements != null) {
            for (Node s : statements) {
                addChild(s);
            }
        }
    }
}
