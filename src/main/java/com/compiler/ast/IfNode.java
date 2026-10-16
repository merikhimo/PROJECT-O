package main.java.com.compiler.ast;

import java.util.List;

public class IfNode extends Node {
    public Node condition;
    public List<Node> thenBranch;
    public List<Node> elseBranch;

    public IfNode(Node condition, List<Node> thenBranch, List<Node> elseBranch) {
        super("IfStatement");
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;

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