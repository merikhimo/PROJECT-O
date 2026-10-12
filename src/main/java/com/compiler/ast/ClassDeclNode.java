package main.java.com.compiler.ast;

import java.util.List;

class ClassDeclNode extends Node {
    public String className;
    public String superClassName;

    public ClassDeclNode(String className, String superClassName, List<Node> members) {
        super("ClassDeclaration");
        this.className = className;
        this.superClassName = superClassName;
        if (members != null) {
            for (Node m : members) {
                addChild(m);
            }
        }
    }
}