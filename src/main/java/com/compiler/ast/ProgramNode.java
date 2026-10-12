package main.java.com.compiler.ast;

import java.util.List;

public class ProgramNode extends Node {
    public ProgramNode(List<Node> classes) {
        super("Program");
        if (classes != null) {
            for (Node c : classes) {
                addChild(c);
            }
        }
    }
}