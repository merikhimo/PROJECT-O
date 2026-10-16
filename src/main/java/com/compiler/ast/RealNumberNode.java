package main.java.com.compiler.ast;

public class RealNumberNode extends Node {
    public double value;

    public RealNumberNode(double value) {
        super("RealLiteral");
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "RealLiteral (" + value + ")");
    }
}
