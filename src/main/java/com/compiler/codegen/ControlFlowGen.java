package main.java.com.compiler.codegen;

import main.java.com.compiler.ast.*;

public class ControlFlowGen {
    private final CodeGenerator g;

    public ControlFlowGen(CodeGenerator g) { this.g = g; }

    /** Условие: g.genExpr(n.condition); ветки: g.genBlock(n.thenBranch) / g.genBlock(n.elseBranch) (может быть null). */
    public void genIf(IfNode n) {
        throw new UnsupportedOperationException("TODO if");
    }

    public void genWhile(WhileNode n) {
        throw new UnsupportedOperationException("TODO while");
    }
}
