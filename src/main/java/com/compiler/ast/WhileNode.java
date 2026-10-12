package main.java.com.compiler.ast;

import java.util.List;

class WhileNode extends Node {
    public WhileNode(Node condition, List<Node> body) {
        super("WhileLoop");
        addChild(condition);
        if (body != null) {
            for (Node s : body) {
                addChild(s);
            }
        }
    }
}
