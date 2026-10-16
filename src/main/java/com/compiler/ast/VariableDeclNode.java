package main.java.com.compiler.ast;

public class VariableDeclNode extends Node {
    public String varName;
    public Node initializer;

    public VariableDeclNode(String varName, Node initializer) {
        super("VariableDeclaration");
        this.varName = varName;
        this.initializer = initializer;

        addChild(initializer);
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "VariableDeclaration (name: " + varName + ")");
        if (initializer != null) {
            initializer.print(indent + "  ");
        }
    }
}
