package main.java.com.compiler.ast;

public class BooleanNumberNode extends Node {
    public boolean value;

    public BooleanNumberNode(boolean value) {
        super("BooleanLiteral");
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "BooleanLiteral (" + value + ")");
    }
}
