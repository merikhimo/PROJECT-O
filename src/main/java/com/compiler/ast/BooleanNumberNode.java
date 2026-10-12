package main.java.com.compiler.ast;

class BooleanNumberNode extends Node {
    public boolean value;

    public BooleanNumberNode(boolean value) {
        super("BooleanLiteral");
        this.value = value;
    }
}
