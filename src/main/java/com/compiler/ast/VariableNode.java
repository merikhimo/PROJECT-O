package main.java.com.compiler.ast;

class VariableNode extends Node {
    public String varName;

    public VariableNode(String varName) {
        super("Variable");
        this.varName = varName;
    }
}
