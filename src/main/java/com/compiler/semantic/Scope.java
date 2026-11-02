package main.java.com.compiler.semantic;

import java.util.*;

public class Scope {
    private final Scope parent;
    private final Map<String, VarSymbol> vars = new LinkedHashMap<>();

    public Scope(Scope parent) { this.parent = parent; }

    public Scope getParent() { return parent; }

    public boolean declare(VarSymbol v) {
        if (vars.containsKey(v.name)) return false;
        vars.put(v.name, v);
        return true;
    }

    public VarSymbol lookup(String name) {
        for (Scope s = this; s != null; s = s.parent) {
            VarSymbol v = s.vars.get(name);
            if (v != null) return v;
        }
        return null;
    }
}