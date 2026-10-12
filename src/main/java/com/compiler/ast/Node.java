package main.java.com.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String type;
    public List<Node> children = new ArrayList<>();

    public Node(String type) {
        this.type = type;
    }

    public void addChild(Node child) {
        if (child != null) children.add(child);
    }

    public void print(String indent) {
        System.out.println(indent + type);
        for (Node child : children) {
            if (child != null) {
                child.print(indent + "  ");
            }
        }
    }
}
