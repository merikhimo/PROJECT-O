package main.java.com.compiler.ast;

import java.util.List;

public class ClassDeclNode extends Node {
    public String className;
    public String superClassName;
    public List<Node> members;

    public ClassDeclNode(String className, String superClassName, List<Node> members) {
        super("ClassDeclaration");
        this.className = className;
        this.superClassName = superClassName;
        this.members = members;

        if (members != null) {
            for (Node m : members) {
                addChild(m);
            }
        }
    }

    @Override
    public void print(String indent) {
        String inheritInfo = (superClassName != null) ? " extends " + superClassName : "";
        System.out.println(indent + "ClassDeclaration (" + className + inheritInfo + ")");
        for (Node child : children) {
            if (child != null) child.print(indent + "  ");
        }
    }
}