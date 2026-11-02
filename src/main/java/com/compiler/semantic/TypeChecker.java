package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.*;
import java.util.*;

public class TypeChecker extends BaseVisitor<String> {
    public final SymbolTable table;
    public final ErrorReporter errors;

    public ClassSymbol currentClass;
    public Scope scope;
    public String returnType;
    public boolean inConstructor;
    public boolean inFieldInit;

    public TypeChecker(SymbolTable table, ErrorReporter errors) {
        this.table = table;
        this.errors = errors;
    }


    public void check(Node program) {
        List<ClassDeclNode> classes = new ArrayList<>();
        for (Node n : program.children) {
            if (n instanceof ClassDeclNode) classes.add((ClassDeclNode) n);
        }
        for (ClassDeclNode c : classes) inferFieldTypes(c);
        for (ClassDeclNode c : classes) checkClass(c);
    }

    public void enterClass(ClassDeclNode c) {
        currentClass = table.classes.get(c.className);
        scope = new Scope(null);
        returnType = null;
        inConstructor = false;
        inFieldInit = false;
    }

    public void inferFieldTypes(ClassDeclNode c) {
        enterClass(c);
        inFieldInit = true;
        for (Node m : c.members) {
            if (m instanceof VariableDeclNode) {
                VariableDeclNode v = (VariableDeclNode) m;
                String t = value(v.initializer);
                v.resolvedType = t;
                currentClass.fields.get(v.varName).typeName = t;
            }
        }
        inFieldInit = false;
    }

    public void checkClass(ClassDeclNode c) {
        for (Node m : c.members) {
            if (m instanceof MethodDeclNode) checkMethod(c, (MethodDeclNode) m);
            else if (m instanceof ConstructNode) checkConstructor(c, (ConstructNode) m);
        }
    }

    public void checkMethod(ClassDeclNode c, MethodDeclNode m) {
        if (m.body == null) return;
        enterClass(c);
        MethodHeaderNode h = m.header;
        returnType = (h.returnType == null) ? "void" : h.returnType;
        declareParams(h.parameters);

        if (m.body instanceof ExpressionBodyNode) {
            Node e = m.body.children.get(0);
            if (returnType.equals("void")) {
                e.accept(this);
            } else {
                String t = value(e);
                if (t != null && !table.isAssignable(t, returnType)) {
                    errors.error(e, "method '" + h.methodName + "' returns '" + returnType
                            + "' but the expression has type '" + t + "'");
                }
            }
        } else {
            List<Node> stmts = m.body.children;
            for (Node s : stmts) s.accept(this);
            if (!returnType.equals("void") && !alwaysReturns(stmts)) {
                errors.error(m, "method '" + h.methodName + "' must return a value of type '"
                        + returnType + "' on every path");
            }
        }
    }

    public void checkConstructor(ClassDeclNode c, ConstructNode k) {
        enterClass(c);
        inConstructor = true;
        declareParams(k.parameters);
        if (k.statements != null) {
            for (Node s : k.statements) s.accept(this);
        }
    }

    public void declareParams(List<Node> params) {
        if (params == null) return;
        for (Node p : params) {
            ParameterNode pn = (ParameterNode) p;
            scope.declare(new VarSymbol(pn.paramName, pn.paramType));
        }
    }


    @Override
    public String visit(VariableDeclNode n) {
        String t = value(n.initializer);
        n.resolvedType = t;
        if (!scope.declare(new VarSymbol(n.varName, t))) {
            errors.error(n, "variable '" + n.varName + "' is already declared in this scope");
        }
        return null;
    }

    @Override
    public String visit(AssignmentNode n) {
        String targetType;
        VarSymbol local = scope.lookup(n.target);
        if (local != null) {
            targetType = local.typeName;
        } else {
            VarSymbol f = table.findField(currentClass.name, n.target);
            if (f == null) {
                errors.error(n, "variable '" + n.target + "' is not declared");
                value(n.expression);
                return null;
            }
            targetType = f.typeName;
        }
        String t = value(n.expression);
        if (t != null && targetType != null && !table.isAssignable(t, targetType)) {
            errors.error(n, "cannot assign '" + t + "' to variable '" + n.target
                    + "' of type '" + targetType + "'");
        }
        return null;
    }

    @Override
    public String visit(WhileNode n) {
        checkCondition(n.condition, "while");
        checkBlock(n.body);
        return null;
    }

    @Override
    public String visit(IfNode n) {
        checkCondition(n.condition, "if");
        checkBlock(n.thenBranch);
        checkBlock(n.elseBranch);
        return null;
    }

    @Override
    public String visit(ReturnNode n) {
        boolean hasExpr = !n.children.isEmpty();
        if (inConstructor) {
            if (hasExpr) errors.error(n, "a constructor cannot return a value");
            return null;
        }
        if (returnType == null) {
            errors.error(n, "'return' is only allowed inside a method");
            return null;
        }
        if (hasExpr) {
            String t = value(n.children.get(0));
            if (returnType.equals("void")) {
                errors.error(n, "this method returns no value, but 'return' has an expression");
            } else if (t != null && !table.isAssignable(t, returnType)) {
                errors.error(n, "cannot return '" + t + "' from a method returning '" + returnType + "'");
            }
        } else if (!returnType.equals("void")) {
            errors.error(n, "'return' without a value in a method returning '" + returnType + "'");
        }
        return null;
    }

    public void checkCondition(Node cond, String keyword) {
        String t = value(cond);
        if (t != null && !t.equals("Boolean")) {
            errors.error(cond, "'" + keyword + "' condition must be Boolean, found '" + t + "'");
        }
    }

    public void checkBlock(List<Node> stmts) {
        Scope saved = scope;
        scope = new Scope(saved);
        if (stmts != null) {
            for (Node s : stmts) s.accept(this);
        }
        scope = saved;
    }

    public boolean alwaysReturns(List<Node> stmts) {
        if (stmts == null || stmts.isEmpty()) return false;
        Node last = stmts.get(stmts.size() - 1);
        if (last instanceof ReturnNode) return true;
        if (last instanceof IfNode) {
            IfNode i = (IfNode) last;
            return i.elseBranch != null && !i.elseBranch.isEmpty()
                    && alwaysReturns(i.thenBranch) && alwaysReturns(i.elseBranch);
        }
        return false;
    }


    @Override public String visit(NumberNode n)        { return n.resolvedType = "Integer"; }
    @Override public String visit(RealNumberNode n)    { return n.resolvedType = "Real"; }
    @Override public String visit(BooleanNumberNode n) { return n.resolvedType = "Boolean"; }

    @Override
    public String visit(ThisNode n) {
        if (inFieldInit) {
            errors.error(n, "'this' cannot be used in a field initializer");
            return null;
        }
        return n.resolvedType = currentClass.name;
    }

    @Override
    public String visit(VariableNode n) {
        String name = nameOf(n);
        VarSymbol v = scope.lookup(name);
        if (v != null) return n.resolvedType = v.typeName;

        VarSymbol f = table.findField(currentClass.name, name);
        if (f != null) return n.resolvedType = fieldType(n, f);

        if (table.classes.containsKey(name)) {
            errors.error(n, "class name '" + name + "' cannot be used as a value");
        } else {
            errors.error(n, "variable '" + name + "' is not declared");
        }
        return null;
    }

    @Override
    public String visit(MemberAccessNode n) {
        String member = nameOf((VariableNode) n.right);

        String cls = asClassName(n.left);
        if (cls != null) {
            ClassSymbol cs = table.classes.get(cls);
            VarSymbol f = cs.fields.get(member);
            if (cs.builtin && f != null) return n.resolvedType = f.typeName;
            errors.error(n, "'" + member + "' is not a static member of class '" + cls + "'");
            return null;
        }

        String rt = value(n.left);
        if (rt == null) return null;

        VarSymbol f = table.findField(rt, member);
        if (f != null) return n.resolvedType = fieldType(n, f);

        MethodSymbol m = table.findMethod(rt, member, new ArrayList<String>());
        if (m != null) return n.resolvedType = m.returnType;

        errors.error(n, "class '" + rt + "' has no member '" + member + "'");
        return null;
    }

    @Override
    public String visit(FunctionCallNode n) {
        Node callee = n.expression;

        if (callee instanceof VariableNode) {
            String name = nameOf((VariableNode) callee);
            if (name.equals("Print")) {
                argTypes(n.arguments);
                if (n.arguments.size() != 1) {
                    errors.error(n, "Print expects exactly one argument");
                }
                return n.resolvedType = "void";
            }
            if (table.classes.containsKey(name)) {
                return n.resolvedType = checkConstructorCall(n, name);
            }
            errors.error(n, "'" + name + "' is not a class (call methods through an object, e.g. this."
                    + name + "(...))");
            argTypes(n.arguments);
            return null;
        }

        if (callee instanceof MemberAccessNode) {
            MemberAccessNode ma = (MemberAccessNode) callee;
            String method = nameOf((VariableNode) ma.right);

            String cls = asClassName(ma.left);
            if (cls != null) {
                errors.error(n, "cannot call '" + method + "' on class name '" + cls
                        + "': there are no static methods");
                argTypes(n.arguments);
                return null;
            }

            String rt = value(ma.left);
            List<String> args = argTypes(n.arguments);
            if (rt == null || args == null) return null;

            MethodSymbol m = table.findMethod(rt, method, args);
            if (m == null) {
                errors.error(n, "class '" + rt + "' has no method " + method + sig(args));
                return null;
            }
            ma.resolvedType = m.returnType;
            return n.resolvedType = m.returnType;
        }

        errors.error(n, "this expression cannot be called");
        return null;
    }

    public String checkConstructorCall(FunctionCallNode n, String cls) {
        List<String> args = argTypes(n.arguments);
        if (args != null && table.findConstructor(cls, args) == null) {
            errors.error(n, "class '" + cls + "' has no constructor " + cls + sig(args));
        }
        return cls;
    }


    public String value(Node n) {
        if (n == null) return null;
        String t = n.accept(this);
        if ("void".equals(t)) {
            errors.error(n, "an expression of type void cannot be used as a value");
            return null;
        }
        return t;
    }

    public List<String> argTypes(List<Node> args) {
        List<String> types = new ArrayList<>();
        boolean ok = true;
        if (args != null) {
            for (Node a : args) {
                String t = value(a);
                if (t == null) ok = false; else types.add(t);
            }
        }
        return ok ? types : null;
    }

    public String asClassName(Node left) {
        if (!(left instanceof VariableNode)) return null;
        String name = nameOf((VariableNode) left);
        if (scope.lookup(name) != null) return null;
        if (table.findField(currentClass.name, name) != null) return null;
        return table.classes.containsKey(name) ? name : null;
    }

    public String fieldType(Node at, VarSymbol f) {
        if (f.typeName == null && !errors.hasErrors()) {
            errors.error(at, "type of field '" + f.name + "' is not known here (used before its declaration)");
        }
        return f.typeName;
    }

    public String sig(List<String> types) {
        return "(" + String.join(", ", types) + ")";
    }

    public String nameOf(VariableNode v) {
        return v.varName;
    }
}