package main.java.com.compiler.semantic;

import java.util.*;

public class SymbolTable {
    public final Map<String, ClassSymbol> classes = new LinkedHashMap<>();

    public SymbolTable() { registerBuiltins(); }

    public ClassSymbol getClass(String name) { return classes.get(name); }

    public boolean isSubclass(String sub, String sup) {
        Set<String> seen = new HashSet<>();
        String cur = sub;
        while (cur != null && seen.add(cur)) {
            if (cur.equals(sup)) return true;
            ClassSymbol c = classes.get(cur);
            cur = (c == null) ? null : c.superName;
        }
        return false;
    }

    public boolean isPrimitive(String t) {
        return t.equals("Integer") || t.equals("Real") || t.equals("Boolean");
    }

    public boolean isAssignable(String from, String to) {
        if (from == null || to == null) return true;
        if (from.equals(to)) return true;
        if (from.equals("null")) return !isPrimitive(to);
        return isSubclass(from, to);
    }

    public VarSymbol findField(String className, String name) {
        Set<String> seen = new HashSet<>();
        String cur = className;
        while (cur != null && seen.add(cur)) {
            ClassSymbol c = classes.get(cur);
            if (c == null) return null;
            VarSymbol f = c.fields.get(name);
            if (f != null) return f;
            cur = c.superName;
        }
        return null;
    }

    public MethodSymbol findMethod(String className, String name, List<String> argTypes) {
        Set<String> seen = new HashSet<>();
        String cur = className;
        while (cur != null && seen.add(cur)) {
            ClassSymbol c = classes.get(cur);
            if (c == null) return null;
            List<MethodSymbol> overloads = c.methods.get(name);
            if (overloads != null) {
                for (MethodSymbol m : overloads) {
                    if (matches(m.paramTypes, argTypes)) return m;
                }
            }
            cur = c.superName;
        }
        return null;
    }

    public MethodSymbol findConstructor(String className, List<String> argTypes) {
        ClassSymbol c = classes.get(className);
        if (c == null) return null;
        for (MethodSymbol m : c.constructors) {
            if (matches(m.paramTypes, argTypes)) return m;
        }
        return null;
    }

    public boolean matches(List<String> params, List<String> args) {
        if (params.size() != args.size()) return false;
        for (int i = 0; i < params.size(); i++) {
            if (!isAssignable(args.get(i), params.get(i))) return false;
        }
        return true;
    }


    public ClassSymbol builtin(String name) {
        ClassSymbol c = new ClassSymbol(name, null, true);
        classes.put(name, c);
        return c;
    }

    public void m(ClassSymbol c, String name, String ret, String... params) {
        c.addMethod(new MethodSymbol(name, ret, Arrays.asList(params), true));
    }

    public void ctor(ClassSymbol c, String... params) {
        c.constructors.add(new MethodSymbol(c.name, c.name, Arrays.asList(params), true));
    }

    public void registerBuiltins() {
        ClassSymbol integer = builtin("Integer");
        ClassSymbol real = builtin("Real");
        ClassSymbol bool = builtin("Boolean");

        ctor(integer, "Integer");
        ctor(integer, "Real");
        ctor(real, "Real");
        ctor(real, "Integer");
        ctor(bool, "Boolean");

        for (String op : new String[]{"Plus", "Minus", "Mult", "Div"}) {
            m(integer, op, "Integer", "Integer");
            m(integer, op, "Real", "Real");
            m(real, op, "Real", "Real");
            m(real, op, "Real", "Integer");
        }
        m(integer, "Rem", "Integer", "Integer");
        m(real, "Rem", "Real", "Integer");
        integer.fields.put("Min", new VarSymbol("Min", "Integer"));
        integer.fields.put("Max", new VarSymbol("Max", "Integer"));
        real.fields.put("Min", new VarSymbol("Min", "Real"));
        real.fields.put("Max", new VarSymbol("Max", "Real"));
        real.fields.put("Epsilon", new VarSymbol("Epsilon", "Real"));

        for (String op : new String[]{"Less", "LessEqual", "Greater", "GreaterEqual", "Equal"}) {
            m(integer, op, "Boolean", "Integer");
            m(integer, op, "Boolean", "Real");
            m(real, op, "Boolean", "Real");
            m(real, op, "Boolean", "Integer");
        }
        m(integer, "UnaryMinus", "Integer");
        m(integer, "toReal", "Real");
        m(integer, "toBoolean", "Boolean");
        m(real, "UnaryMinus", "Real");
        m(real, "toInteger", "Integer");

        m(bool, "And", "Boolean", "Boolean");
        m(bool, "Or", "Boolean", "Boolean");
        m(bool, "Xor", "Boolean", "Boolean");
        m(bool, "Not", "Boolean");
        m(bool, "toInteger", "Integer");

        arrayClass("IntArray", "Integer");
        arrayClass("RealArray", "Real");
        arrayClass("BoolArray", "Boolean");
    }

    public void arrayClass(String name, String elem) {
        ClassSymbol a = builtin(name);
        ctor(a, "Integer");
        m(a, "Length", "Integer");
        m(a, "get", elem, "Integer");
        m(a, "set", "void", "Integer", elem);
    }

    public void dump() {
        for (ClassSymbol c : classes.values()) {
            if (c.builtin) continue;
            System.out.println("class " + c.name + (c.superName != null ? " extends " + c.superName : ""));
            for (VarSymbol f : c.fields.values())
                System.out.println("  field " + f.name);
            for (List<MethodSymbol> l : c.methods.values())
                for (MethodSymbol m : l)
                    System.out.println("  method " + m.name + m.paramTypes + " : " + m.returnType
                            + (m.hasBody ? "" : " [forward]"));
            for (MethodSymbol k : c.constructors)
                System.out.println("  constructor " + k.paramTypes);
        }
    }
}