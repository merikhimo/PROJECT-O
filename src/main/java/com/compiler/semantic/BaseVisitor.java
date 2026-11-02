package main.java.com.compiler.semantic;

import main.java.com.compiler.ast.*;

public class BaseVisitor<R> implements Visitor<R> {
    public R visitChildren(Node n) {
        for (Node child : n.children) {
            if (child != null) child.accept(this);
        }
        return null;
    }

    @Override public R visit(Node n)                  { return visitChildren(n); }
    @Override public R visit(ProgramNode n)           { return visitChildren(n); }
    @Override public R visit(ClassDeclNode n)         { return visitChildren(n); }
    @Override public R visit(VariableDeclNode n)      { return visitChildren(n); }
    @Override public R visit(MethodDeclNode n)        { return visitChildren(n); }
    @Override public R visit(MethodHeaderNode n)      { return visitChildren(n); }
    @Override public R visit(MethodBodyNode n)        { return visitChildren(n); }
    @Override public R visit(ExpressionBodyNode n)    { return visitChildren(n); }
    @Override public R visit(ConstructNode n)         { return visitChildren(n); }
    @Override public R visit(ConstructorInvocNode n)  { return visitChildren(n); }
    @Override public R visit(ParameterNode n)         { return visitChildren(n); }
    @Override public R visit(AssignmentNode n)        { return visitChildren(n); }
    @Override public R visit(WhileNode n)             { return visitChildren(n); }
    @Override public R visit(IfNode n)                { return visitChildren(n); }
    @Override public R visit(ReturnNode n)            { return visitChildren(n); }
    @Override public R visit(MemberAccessNode n)      { return visitChildren(n); }
    @Override public R visit(FunctionCallNode n)      { return visitChildren(n); }
    @Override public R visit(VariableNode n)          { return visitChildren(n); }
    @Override public R visit(ThisNode n)              { return visitChildren(n); }
    @Override public R visit(NumberNode n)            { return visitChildren(n); }
    @Override public R visit(RealNumberNode n)        { return visitChildren(n); }
    @Override public R visit(BooleanNumberNode n)     { return visitChildren(n); }
}