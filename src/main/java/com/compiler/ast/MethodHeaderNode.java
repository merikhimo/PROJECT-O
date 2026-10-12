package main.java.com.compiler.ast;

import java.util.List;

class MethodHeaderNode extends Node {
    public String methodName;
    public String returnType;

    public MethodHeaderNode(String methodName, List<Node> parameters, String returnType) {
        super("MethodHeader");
        this.methodName = methodName;
        this.returnType = returnType;
        if (parameters != null) {
            for (Node p : parameters) {
                addChild(p);
            }
        }
    }
}
