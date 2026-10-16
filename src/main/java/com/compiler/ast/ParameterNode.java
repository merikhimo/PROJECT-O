package main.java.com.compiler.ast;

public class ParameterNode extends Node {
    public String paramName;
    public String paramType;

    public ParameterNode(String paramName, String paramType) {
        super("Parameter");
        this.paramName = paramName;
        this.paramType = paramType;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Parameter (name: " + paramName + ", type: " + paramType + ")");
    }
}
