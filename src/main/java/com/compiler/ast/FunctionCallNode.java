package main.java.com.compiler.ast;

import java.util.List;

class FunctionCallNode extends Node {
    public FunctionCallNode(Node expression, List<Node> arguments) {
        super("FunctionCall");
        addChild(expression);
        if (arguments != null) {
            for (Node a : arguments) {
                addChild(a);
            }
        }
    }
}