package main.java.com.compiler.ast;

import java.util.List;

class IfNode extends Node {
    public IfNode(Node condition, List<Node> thenBranch, List<Node> elseBranch) {
        super("IfStatement");
        addChild(condition);
        if (thenBranch != null) {
            for (Node s : thenBranch) {
                addChild(s);
            }
        }
        if (elseBranch != null) {
            for (Node s : elseBranch) {
                addChild(s);
            }
        }
    }
}