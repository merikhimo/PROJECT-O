package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.*;
import java.util.ArrayList;
import java.util.List;

public class Optimizer implements Visitor<Node> {
    public boolean changed;

    public Node run(Node root) {
        changed = false;
        return root.accept(this);
    }


    public List<Node> visitList(List<Node> list) {
        List<Node> out = new ArrayList<>();
        if (list == null) return out;
        for (int idx = 0; idx < list.size(); idx++) {
            Node s = list.get(idx);
            if (s == null) continue;
            Node r = s.accept(this);
            if (r == null) { changed = true; continue; }
            if (r instanceof IfNode && ((IfNode) r).condition instanceof BooleanNumberNode) {
                IfNode i = (IfNode) r;
                boolean v = ((BooleanNumberNode) i.condition).value;
                List<Node> branch = v ? i.thenBranch : i.elseBranch;
                if (branch == null || !hasDecl(branch)) {
                    if (branch != null) out.addAll(branch);
                    changed = true;
                    continue;
                }
            }
            out.add(r);
            if (r instanceof ReturnNode) {
                if (idx < list.size() - 1) changed = true;
                break;
            }
        }
        return out;
    }

    public List<Node> visitExprs(List<Node> list) {
        List<Node> out = new ArrayList<>();
        if (list != null) {
            for (Node e : list) if (e != null) out.add(e.accept(this));
        }
        return out;
    }

    public boolean hasDecl(List<Node> block) {
        for (Node s : block) if (s instanceof VariableDeclNode) return true;
        return false;
    }

    public <T extends Node> T keep(T fresh, Node old) {
        fresh.line = old.line;
        fresh.column = old.column;
        fresh.resolvedType = old.resolvedType;
        return fresh;
    }

    public <T extends Node> T at(T fresh, Node old) {
        fresh.line = old.line;
        fresh.column = old.column;
        return fresh;
    }

    public Node intLit(int v)      { NumberNode n = new NumberNode(v); n.resolvedType = "Integer"; return n; }
    public Node realLit(double v)  { RealNumberNode n = new RealNumberNode(v); n.resolvedType = "Real"; return n; }
    public Node boolLit(boolean v) { BooleanNumberNode n = new BooleanNumberNode(v); n.resolvedType = "Boolean"; return n; }

    public boolean isNum(Node n) { return n instanceof NumberNode || n instanceof RealNumberNode; }
    public double num(Node n) {
        return (n instanceof NumberNode) ? ((NumberNode) n).value : ((RealNumberNode) n).value;
    }


    @Override public Node visit(Node n) { return n; }

    @Override public Node visit(ProgramNode n) {
        return keep(new ProgramNode(visitList(n.children)), n);
    }

    @Override public Node visit(ClassDeclNode n) {
        return keep(new ClassDeclNode(n.className, n.superClassName, visitList(n.members)), n);
    }

    @Override public Node visit(VariableDeclNode n) {
        Node init = (n.initializer == null) ? null : n.initializer.accept(this);
        return keep(new VariableDeclNode(n.varName, init), n);
    }

    @Override public Node visit(MethodDeclNode n) {
        Node body = (n.body == null) ? null : n.body.accept(this);
        return keep(new MethodDeclNode(n.header, body), n);
    }

    @Override public Node visit(MethodHeaderNode n) { return n; }

    @Override public Node visit(MethodBodyNode n) {
        return keep(new MethodBodyNode(visitList(n.children)), n);
    }

    @Override public Node visit(ExpressionBodyNode n) {
        return keep(new ExpressionBodyNode(n.children.get(0).accept(this)), n);
    }

    @Override public Node visit(ConstructNode n) {
        return keep(new ConstructNode(n.parameters, visitList(n.statements)), n);
    }

    @Override public Node visit(ConstructorInvocNode n) {
        return keep(new ConstructorInvocNode(n.className, visitExprs(n.arguments)), n);
    }

    @Override public Node visit(ParameterNode n) { return n; }

    @Override public Node visit(AssignmentNode n) {
        return keep(new AssignmentNode(n.target, n.expression.accept(this)), n);
    }

    @Override public Node visit(ReturnNode n) {
        Node e = n.children.isEmpty() ? null : n.children.get(0).accept(this);
        return keep(new ReturnNode(e), n);
    }

    @Override public Node visit(MemberAccessNode n) {
        return keep(new MemberAccessNode(n.left.accept(this), n.right), n);
    }

    @Override public Node visit(VariableNode n)      { return n; }
    @Override public Node visit(ThisNode n)          { return n; }
    @Override public Node visit(NumberNode n)        { return n; }
    @Override public Node visit(RealNumberNode n)    { return n; }
    @Override public Node visit(BooleanNumberNode n) { return n; }


    @Override public Node visit(WhileNode n) {
        Node cond = n.condition.accept(this);
        if (cond instanceof BooleanNumberNode && !((BooleanNumberNode) cond).value) {
            changed = true;
            return null;
        }
        return keep(new WhileNode(cond, visitList(n.body)), n);
    }

    @Override public Node visit(IfNode n) {
        Node cond = n.condition.accept(this);
        List<Node> thenB = visitList(n.thenBranch);
        List<Node> elseB = (n.elseBranch == null) ? null : visitList(n.elseBranch);
        return keep(new IfNode(cond, thenB, elseB), n);
    }


    @Override public Node visit(FunctionCallNode n) {
        FunctionCallNode call = keep(
                new FunctionCallNode(n.expression.accept(this), visitExprs(n.arguments)), n);
        return fold(call);
    }

    public Node fold(FunctionCallNode call) {
        if (!(call.expression instanceof MemberAccessNode)) return call;
        MemberAccessNode ma = (MemberAccessNode) call.expression;
        if (!(ma.right instanceof VariableNode)) return call;
        String op = ((VariableNode) ma.right).varName;
        List<Node> args = (call.arguments == null) ? new ArrayList<Node>() : call.arguments;

        Node folded = null;
        if (args.size() == 1) folded = foldBinary(op, ma.left, args.get(0));
        else if (args.isEmpty()) folded = foldUnary(op, ma.left);
        if (folded == null) return call;

        changed = true;
        return at(folded, call);
    }

    public Node foldBinary(String op, Node l, Node r) {
        if (l instanceof NumberNode && r instanceof NumberNode) {
            int a = ((NumberNode) l).value, b = ((NumberNode) r).value;
            switch (op) {
                case "Plus":         return intLit(a + b);
                case "Minus":        return intLit(a - b);
                case "Mult":         return intLit(a * b);
                case "Div":          return b != 0 ? intLit(a / b) : null;
                case "Rem":          return b != 0 ? intLit(a % b) : null;
                case "Less":         return boolLit(a < b);
                case "LessEqual":    return boolLit(a <= b);
                case "Greater":      return boolLit(a > b);
                case "GreaterEqual": return boolLit(a >= b);
                case "Equal":        return boolLit(a == b);
            }
            return null;
        }
        if (isNum(l) && isNum(r)) {
            double a = num(l), b = num(r);
            switch (op) {
                case "Plus":         return realLit(a + b);
                case "Minus":        return realLit(a - b);
                case "Mult":         return realLit(a * b);
                case "Div":          return b != 0.0 ? realLit(a / b) : null;
                case "Less":         return boolLit(a < b);
                case "LessEqual":    return boolLit(a <= b);
                case "Greater":      return boolLit(a > b);
                case "GreaterEqual": return boolLit(a >= b);
                case "Equal":        return boolLit(a == b);
            }
            return null;
        }
        if (l instanceof BooleanNumberNode && r instanceof BooleanNumberNode) {
            boolean a = ((BooleanNumberNode) l).value, b = ((BooleanNumberNode) r).value;
            switch (op) {
                case "And": return boolLit(a && b);
                case "Or":  return boolLit(a || b);
                case "Xor": return boolLit(a ^ b);
            }
            return null;
        }
        return foldIdentity(op, l, r);
    }

    public Node foldUnary(String op, Node l) {
        if (op.equals("Not") && l instanceof BooleanNumberNode) {
            return boolLit(!((BooleanNumberNode) l).value);
        }
        if (op.equals("UnaryMinus")) {
            if (l instanceof NumberNode) return intLit(-((NumberNode) l).value);
            if (l instanceof RealNumberNode) return realLit(-((RealNumberNode) l).value);
        }
        return null;
    }

    public Node foldIdentity(String op, Node l, Node r) {
        if (r instanceof NumberNode && "Integer".equals(l.resolvedType)) {
            int v = ((NumberNode) r).value;
            if ((v == 0 && (op.equals("Plus") || op.equals("Minus")))
                    || (v == 1 && (op.equals("Mult") || op.equals("Div")))) return l;
        }
        if (l instanceof NumberNode && "Integer".equals(r.resolvedType)) {
            int v = ((NumberNode) l).value;
            if ((v == 0 && op.equals("Plus")) || (v == 1 && op.equals("Mult"))) return r;
        }
        return null;
    }
}