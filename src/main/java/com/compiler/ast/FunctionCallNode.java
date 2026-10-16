package main.java.com.compiler.ast;

import java.util.List;

public class FunctionCallNode extends Node {
    public Node expression;
    public List<Node> arguments;

    public FunctionCallNode(Node expression, List<Node> arguments) {
        super("FunctionCall");
        this.expression = expression;
        this.arguments = arguments;

        addChild(expression);
        if (arguments != null) {
            for (Node a : arguments) {
                addChild(a);
            }
        }
    }
}