package main.java.com.compiler.ast;

public class VariableNode extends Node {
    public String varName;

    public VariableNode(String varName) {
        super("Variable");
        this.varName = varName;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Variable (" + varName + ")");
    }
}
