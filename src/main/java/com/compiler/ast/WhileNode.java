package main.java.com.compiler.ast;

import java.util.List;

public class WhileNode extends Node {
    public Node condition;
    public List<Node> body;

    public WhileNode(Node condition, List<Node> body) {
        super("WhileLoop");
        this.condition = condition;
        this.body = body;

        addChild(condition);
        if (body != null) {
            for (Node s : body) {
                addChild(s);
            }
        }
    }
}
