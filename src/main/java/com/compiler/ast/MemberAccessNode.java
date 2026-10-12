package main.java.com.compiler.ast;

class MemberAccessNode extends Node {
    public MemberAccessNode(Node left, Node right) {
        super("MemberAccess");
        addChild(left);
        addChild(right);
    }
}
