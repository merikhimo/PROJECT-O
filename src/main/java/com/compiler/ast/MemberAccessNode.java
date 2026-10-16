package main.java.com.compiler.ast;

public class MemberAccessNode extends Node {
    public Node left;
    public Node right;

    public MemberAccessNode(Node left, Node right) {
        super("MemberAccess");
        this.left = left;
        this.right = right;

        addChild(left);
        addChild(right);
    }
}
