package main.java.com.compiler.ast;

public interface Visitor<R> {
    R visit(Node n);
    R visit(ProgramNode n);
    R visit(ClassDeclNode n);
    R visit(VariableDeclNode n);
    R visit(MethodDeclNode n);
    R visit(MethodHeaderNode n);
    R visit(MethodBodyNode n);
    R visit(ExpressionBodyNode n);
    R visit(ConstructNode n);
    R visit(ConstructorInvocNode n);
    R visit(ParameterNode n);
    R visit(AssignmentNode n);
    R visit(WhileNode n);
    R visit(IfNode n);
    R visit(ReturnNode n);
    R visit(MemberAccessNode n);
    R visit(FunctionCallNode n);
    R visit(VariableNode n);
    R visit(ThisNode n);
    R visit(NumberNode n);
    R visit(RealNumberNode n);
    R visit(BooleanNumberNode n);
}