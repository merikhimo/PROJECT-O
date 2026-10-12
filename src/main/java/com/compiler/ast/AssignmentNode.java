package main.java.com.compiler.ast;

class AssignmentNode extends Node {
    public String target;

    public AssignmentNode(String target, Node expression) {
        super("Assignment");
        this.target = target;
        addChild(expression);
    }
}
