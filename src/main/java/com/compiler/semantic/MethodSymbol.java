package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.Node;
import java.util.List;

public class MethodSymbol {
    public final String name;
    public final String returnType;
    public final List<String> paramTypes;
    public final boolean builtin;
    public boolean hasBody;
    public Node decl;

    public MethodSymbol(String name, String returnType, List<String> paramTypes, boolean builtin) {
        this.name = name;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.builtin = builtin;
        this.hasBody = builtin;
    }
}