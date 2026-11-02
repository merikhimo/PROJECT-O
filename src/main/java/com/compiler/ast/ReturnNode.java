package main.java.com.compiler.ast;

public class ReturnNode extends Node {
    public ReturnNode(Node expression) {
        super("Return");
        addChild(expression);
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}