package main.java.com.compiler.ast;

public class ExpressionBodyNode extends Node {
    public ExpressionBodyNode(Node expression) {
        super("ExpressionBody");
        addChild(expression);
    }
}
