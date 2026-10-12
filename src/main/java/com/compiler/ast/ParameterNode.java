package main.java.com.compiler.ast;

class ParameterNode extends Node {
    public String paramName;
    public String paramType;

    public ParameterNode(String paramName, String paramType) {
        super("Parameter");
        this.paramName = paramName;
        this.paramType = paramType;
    }
}
