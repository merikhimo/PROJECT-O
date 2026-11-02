package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.*;
import java.util.*;

public class DeclarationCollector {
    private final SymbolTable table;
    private final ErrorReporter errors;

    public DeclarationCollector(SymbolTable table, ErrorReporter errors) {
        this.table = table;
        this.errors = errors;
    }

    public void collect(Node program) {
        List<ClassDeclNode> classes = new ArrayList<>();

        for (Node n : program.children) {
            if (!(n instanceof ClassDeclNode)) continue;
            ClassDeclNode c = (ClassDeclNode) n;
            if (table.classes.containsKey(c.className)) {
                errors.error(c, "class '" + c.className + "' is already declared");
                continue;
            }
            table.classes.put(c.className, new ClassSymbol(c.className, c.superClassName, false));
            classes.add(c);
        }

        for (ClassDeclNode c : classes) checkSuper(c);

        for (ClassDeclNode c : classes) collectMembers(c);
    }

    private void checkSuper(ClassDeclNode c) {
        if (c.superClassName == null) return;
        if (!table.classes.containsKey(c.superClassName)) {
            errors.error(c, "class '" + c.className + "' extends unknown class '" + c.superClassName + "'");
            return;
        }
        Set<String> seen = new HashSet<>();
        String cur = c.className;
        while (cur != null) {
            if (!seen.add(cur)) {
                errors.error(c, "cyclic inheritance involving class '" + c.className + "'");
                return;
            }
            ClassSymbol s = table.classes.get(cur);
            cur = (s == null) ? null : s.superName;
        }
    }

    private void collectMembers(ClassDeclNode c) {
        ClassSymbol cs = table.classes.get(c.className);
        if (c.members != null) {
            for (Node m : c.members) {
                if (m instanceof VariableDeclNode) {
                    VariableDeclNode v = (VariableDeclNode) m;
                    if (cs.fields.containsKey(v.varName)) {
                        errors.error(v, "field '" + v.varName + "' is already declared in class '" + cs.name + "'");
                    } else {
                        cs.fields.put(v.varName, new VarSymbol(v.varName, null));
                    }
                } else if (m instanceof MethodDeclNode) {
                    collectMethod(cs, (MethodDeclNode) m);
                } else if (m instanceof ConstructNode) {
                    collectConstructor(cs, (ConstructNode) m);
                }
            }
        }

        if (cs.constructors.isEmpty()) {
            MethodSymbol def = new MethodSymbol(cs.name, cs.name, new ArrayList<>(), false);
            def.hasBody = true;
            cs.constructors.add(def);
        }

        for (List<MethodSymbol> list : cs.methods.values()) {
            for (MethodSymbol ms : list) {
                if (!ms.hasBody) {
                    errors.error(ms.decl, "method '" + ms.name + "' is declared but never defined");
                }
            }
        }
    }

    private void collectMethod(ClassSymbol cs, MethodDeclNode m) {
        MethodHeaderNode h = m.header;
        List<String> pts = paramTypes(h.parameters);
        String ret = (h.returnType == null) ? "void" : h.returnType;
        if (h.returnType != null && !table.classes.containsKey(h.returnType)) {
            errors.error(h, "unknown return type '" + h.returnType + "' in method '" + h.methodName + "'");
        }
        boolean hasBody = (m.body != null);

        MethodSymbol existing = findExact(cs, h.methodName, pts);
        if (existing == null) {
            MethodSymbol ms = new MethodSymbol(h.methodName, ret, pts, false);
            ms.hasBody = hasBody;
            ms.decl = m;
            cs.addMethod(ms);
        } else if (!existing.returnType.equals(ret)) {
            errors.error(m, "method '" + h.methodName + "' redeclared with a different return type");
        } else if (existing.hasBody && hasBody) {
            errors.error(m, "method '" + h.methodName + pts + "' is already defined in class '" + cs.name + "'");
        } else if (!existing.hasBody && !hasBody) {
            errors.error(m, "method '" + h.methodName + pts + "' is forward-declared twice");
        } else if (hasBody) {
            existing.hasBody = true;
            existing.decl = m;
        }
    }

    private void collectConstructor(ClassSymbol cs, ConstructNode c) {
        List<String> pts = paramTypes(c.parameters);
        for (MethodSymbol e : cs.constructors) {
            if (e.paramTypes.equals(pts)) {
                errors.error(c, "constructor " + pts + " is already declared in class '" + cs.name + "'");
                return;
            }
        }
        MethodSymbol ms = new MethodSymbol(cs.name, cs.name, pts, false);
        ms.hasBody = true;
        ms.decl = c;
        cs.constructors.add(ms);
    }

    private List<String> paramTypes(List<Node> params) {
        List<String> types = new ArrayList<>();
        if (params == null) return types;
        Set<String> names = new HashSet<>();
        for (Node p : params) {
            ParameterNode pn = (ParameterNode) p;
            if (!names.add(pn.paramName)) {
                errors.error(pn, "duplicate parameter '" + pn.paramName + "'");
            }
            if (!table.classes.containsKey(pn.paramType)) {
                errors.error(pn, "unknown type '" + pn.paramType + "' of parameter '" + pn.paramName + "'");
            }
            types.add(pn.paramType);
        }
        return types;
    }

    private MethodSymbol findExact(ClassSymbol cs, String name, List<String> pts) {
        List<MethodSymbol> list = cs.methods.get(name);
        if (list == null) return null;
        for (MethodSymbol m : list) {
            if (m.paramTypes.equals(pts)) return m;
        }
        return null;
    }
}