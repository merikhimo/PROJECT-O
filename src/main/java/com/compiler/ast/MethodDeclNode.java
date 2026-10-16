package main.java.com.compiler.ast;

public class MethodDeclNode extends Node {
    public MethodHeaderNode header;
    public Node body;

    public MethodDeclNode(Node header, Node body) {
        super("MethodDeclaration");
        this.header = (MethodHeaderNode) header;
        this.body = body;

        addChild(header);
        addChild(body);
    }
}
