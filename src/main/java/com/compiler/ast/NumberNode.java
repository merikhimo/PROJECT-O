package main.java.com.compiler.ast;

public class NumberNode extends Node {
    public int value;

    public NumberNode(int value) {
        super("IntLiteral");
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "IntLiteral (" + value + ")");
    }
}
