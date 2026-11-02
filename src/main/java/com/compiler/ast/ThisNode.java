package main.java.com.compiler.ast;

public class ThisNode extends Node {
    public ThisNode() {
        super("This");
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
