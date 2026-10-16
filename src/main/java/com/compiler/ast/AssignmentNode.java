package main.java.com.compiler.ast;

public class AssignmentNode extends Node {
    public String target;
    public Node expression;

    public AssignmentNode(String target, Node expression) {
        super("Assignment");
        this.target = target;
        this.expression = expression;

        addChild(expression);
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Assignment (target: " + target + ")");
        if (expression != null) {
            expression.print(indent + "  ");
        }
    }
}
