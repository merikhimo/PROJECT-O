package main.java.com.compiler.ast;

class NumberNode extends Node {
    public int value;

    public NumberNode(int value) {
        super("IntLiteral");
        this.value = value;
    }
}
