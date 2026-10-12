package main.java.com.compiler.ast;

class MethodDeclNode extends Node {
    public MethodDeclNode(Node header, Node body) {
        super("MethodDeclaration");
        addChild(header);
        addChild(body);
    }
}
