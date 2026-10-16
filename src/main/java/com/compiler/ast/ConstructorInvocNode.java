package main.java.com.compiler.ast;

import java.util.List;

public class ConstructorInvocNode extends Node {
    public String className;
    public List<Node> arguments;

    public ConstructorInvocNode(String className, List<Node> arguments) {
        super("ConstructorInvocation");
        this.className = className;
        this.arguments = arguments;

        if (arguments != null) {
            for (Node a : arguments) {
                addChild(a);
            }
        }
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "ConstructorInvocation (class: " + className + ")");
        for (Node child : children) {
            if (child != null) child.print(indent + "  ");
        }
    }
}
