package main.java.com.compiler.ast;

import java.util.List;

class ConstructorInvocNode extends Node {
    public String className;

    public ConstructorInvocNode(String className, List<Node> arguments) {
        super("ConstructorInvocation");
        this.className = className;
        if (arguments != null) {
            for (Node a : arguments) {
                addChild(a);
            }
        }
    }
}
