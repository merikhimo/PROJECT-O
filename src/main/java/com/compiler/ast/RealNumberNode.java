package main.java.com.compiler.ast;

class RealNumberNode extends Node {
    public double value;

    public RealNumberNode(double value) {
        super("RealLiteral");
        this.value = value;
    }
}
