package main.java.com.compiler.semantic;

import java.util.*;

public class ClassSymbol {
    public final String name;
    public final String superName;
    public final boolean builtin;
    public final Map<String, VarSymbol> fields = new LinkedHashMap<>();
    public final Map<String, List<MethodSymbol>> methods = new LinkedHashMap<>();
    public final List<MethodSymbol> constructors = new ArrayList<>();

    public ClassSymbol(String name, String superName, boolean builtin) {
        this.name = name;
        this.superName = superName;
        this.builtin = builtin;
    }

    public void addMethod(MethodSymbol m) {
        methods.computeIfAbsent(m.name, k -> new ArrayList<>()).add(m);
    }
}