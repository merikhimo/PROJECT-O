package main.java.com.compiler.codegen;

import main.java.com.compiler.ast.*;

import java.util.List;

public class ControlFlowGen {
    private final CodeGenerator g;

    public ControlFlowGen(CodeGenerator g) {
        this.g = g;
    }

    public void genIf(IfNode n) {
        boolean hasElse = n.elseBranch != null && !n.elseBranch.isEmpty();
        String elseLabel = g.out.newLabel();

        g.genExpr(n.condition);
        g.out.emit("ifeq " + elseLabel);
        g.genBlock(n.thenBranch);

        if (!hasElse) {
            g.out.label(elseLabel);
            return;
        }

        String endLabel = null;
        if (!endsWithReturn(n.thenBranch)) {
            endLabel = g.out.newLabel();
            g.out.emit("goto " + endLabel);
        }
        g.out.label(elseLabel);
        g.genBlock(n.elseBranch);
        if (endLabel != null) {
            g.out.label(endLabel);
        }
    }

    public void genWhile(WhileNode n) {
        String start = g.out.newLabel();
        String end = g.out.newLabel();

        g.out.label(start);
        g.genExpr(n.condition);
        g.out.emit("ifeq " + end);
        g.genBlock(n.body);
        g.out.emit("goto " + start);
        g.out.label(end);
    }

    private boolean endsWithReturn(List<Node> stmts) {
        return stmts != null && !stmts.isEmpty() && stmts.getLast() instanceof ReturnNode;
    }
}
