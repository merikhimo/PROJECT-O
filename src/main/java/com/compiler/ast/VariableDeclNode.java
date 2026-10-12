package main.java.com.compiler.ast;

class VariableDeclNode extends Node {
    public String varName;

    public VariableDeclNode(String varName, Node initializer) {
        super("VariableDeclaration");
        this.varName = varName;
        addChild(initializer);
    }
}
