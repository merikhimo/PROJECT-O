package main.java.com.compiler.ast;

class ReturnNode extends Node {
    public ReturnNode(Node expression) {
        super("Return");
        addChild(expression);
    }
}