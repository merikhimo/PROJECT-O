package main.java.com.compiler.ast;

import java.util.List;

public class MethodHeaderNode extends Node {
    public String methodName;
    public String returnType;
    public List<Node> parameters;

    public MethodHeaderNode(String methodName, List<Node> parameters, String returnType) {
        super("MethodHeader");
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;

        if (parameters != null) {
            for (Node p : parameters) {
                addChild(p);
            }
        }
    }

    @Override
    public void print(String indent) {
        String retInfo = (returnType != null) ? " : " + returnType : " : void";
        System.out.println(indent + "MethodHeader (name: " + methodName + retInfo + ")");
        if (parameters != null) {
            for (Node p : parameters) {
                if (p != null) p.print(indent + "  ");
            }
        }
    }
}
