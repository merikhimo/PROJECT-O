package main.java.com.compiler.semantic;

public class VarSymbol {
    public final String name;
    public String typeName;

    public VarSymbol(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
    }
}