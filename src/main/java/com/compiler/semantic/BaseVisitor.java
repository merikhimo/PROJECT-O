package main.java.com.compiler.semantic;

import java.util.ArrayList;
import java.util.List;
import main.java.com.compiler.ast.*;

public class BaseVisitor<R> implements Visitor<R> {
    
    
    private List<Node> optimizeBranch(List<Node> block) {
        if (block == null) return null;
        List<Node> optimizedBlock = new ArrayList<>();
        for (Node statement : block) {
            if (statement != null) {
                Node optimized = (Node) statement.accept((Visitor<Object>) this);
                if (optimized != null) {
                    optimizedBlock.add(optimized);
                }
            }
        }
        return optimizedBlock;
    }

    private void rebuildChildrenFromLists(Node parent, Node condition, List<Node>... blocks) {
        parent.children.clear();
        if (condition != null) parent.children.add(condition);
        for (List<Node> block : blocks) {
            if (block != null) {
                for (Node node : block) {
                    if (node != null) parent.children.add(node);
                }
            }
        }
    }

    public R visitChildren(Node n) {
        if (n.children == null) return null;
        List<Node> optimizedChildren = new ArrayList<>();
        for (Node child : n.children) {
            if (child != null) {
                Node optimizedChild = (Node) child.accept((Visitor<Object>) this);
                if (optimizedChild != null) {
                    optimizedChildren.add(optimizedChild);
                }
            }
        }
        n.children = optimizedChildren;
        return null;
    }

    @Override public R visit(Node n)                  { visitChildren(n); return (R) n; }
    @Override public R visit(ProgramNode n)           { visitChildren(n); return (R) n; }
    @Override public R visit(ClassDeclNode n)         { visitChildren(n); return (R) n; }
    @Override public R visit(VariableDeclNode n)      { visitChildren(n); return (R) n; }
    @Override public R visit(MethodDeclNode n)        { visitChildren(n); return (R) n; }
    @Override public R visit(MethodHeaderNode n)      { visitChildren(n); return (R) n; }
    @Override public R visit(MethodBodyNode n)        { visitChildren(n); return (R) n; }
    @Override public R visit(ExpressionBodyNode n)    { visitChildren(n); return (R) n; }
    @Override public R visit(ConstructNode n)         { visitChildren(n); return (R) n; }
    @Override public R visit(ConstructorInvocNode n)  { visitChildren(n); return (R) n; }
    @Override public R visit(ParameterNode n)         { visitChildren(n); return (R) n; }
    @Override public R visit(AssignmentNode n)        { visitChildren(n); return (R) n; }
    @Override public R visit(ReturnNode n)            { visitChildren(n); return (R) n; }
    @Override public R visit(MemberAccessNode n)      { visitChildren(n); return (R) n; }
    
    @Override public R visit(VariableNode n)          { return (R) n; }
    @Override public R visit(ThisNode n)              { return (R) n; }
    @Override public R visit(NumberNode n)            { return (R) n; }
    @Override public R visit(RealNumberNode n)        { return (R) n; }
    @Override public R visit(BooleanNumberNode n)     { return (R) n; }

  

        @Override 
    public R visit(FunctionCallNode n) {

        if (n.expression != null) {
            n.expression = (Node) n.expression.accept((Visitor<Object>) this);
        }

        n.arguments = optimizeBranch(n.arguments);

        //Я точно не помню как у нас реализуются выражения
        //
        if (n.expression instanceof VariableNode && n.arguments != null) {
            VariableNode opNode = (VariableNode) n.expression;
            
            String op = opNode.varName; 

            Node left = n.arguments.get(0);
            Node right = n.arguments.get(1);


            if (left instanceof NumberNode && right instanceof NumberNode) {
                int lVal = ((NumberNode) left).value;
                int rVal = ((NumberNode) right).value;

                switch (op) {
                    case "+": return (R) new NumberNode(lVal + rVal);
                    case "-": return (R) new NumberNode(lVal - rVal);
                    case "*": return (R) new NumberNode(lVal * rVal);
                    case "/": 
                        if (rVal != 0) return (R) new NumberNode(lVal / rVal);
                        break;
                    case "==": return (R) new BooleanNumberNode(lVal == rVal);
                    case "!=": return (R) new BooleanNumberNode(lVal != rVal);
                    case ">":  return (R) new BooleanNumberNode(lVal > rVal);
                    case "<":  return (R) new BooleanNumberNode(lVal < rVal);
                }
            }

            if (left instanceof RealNumberNode && right instanceof RealNumberNode) {
                double lVal = ((RealNumberNode) left).value;
                double rVal = ((RealNumberNode) right).value;

                switch (op) {
                    case "+": return (R) new RealNumberNode(lVal + rVal);
                    case "-": return (R) new RealNumberNode(lVal - rVal);
                    case "*": return (R) new RealNumberNode(lVal * rVal);
                    case "/": 
                        if (rVal != 0.0) return (R) new RealNumberNode(lVal / rVal);
                        break;
                }
            }

            if (left instanceof NumberNode) {
                int lVal = ((NumberNode) left).value;
                if (op.equals("*") && lVal == 0) return (R) new NumberNode(0);
                if (op.equals("*") && lVal == 1) return (R) right;
                if (op.equals("+") && lVal == 0) return (R) right;
            }
            
            if (right instanceof NumberNode) {
                int rVal = ((NumberNode) right).value;
                if (op.equals("*") && rVal == 0) return (R) new NumberNode(0); 
                if (op.equals("*") && rVal == 1) return (R) left;
                if (op.equals("+") && rVal == 0) return (R) left;
                if (op.equals("-") && rVal == 0) return (R) left;
            }
        }


        rebuildChildrenFromLists(n, n.expression, n.arguments);
        return (R) n;
    }


    @Override 
    public R visit(WhileNode n) {
        if (n.condition != null) {
            n.condition = (Node) n.condition.accept((Visitor<Object>) this);
        }

        if (n.condition instanceof BooleanNumberNode && !((BooleanNumberNode) n.condition).value) { return null; }

        n.body = optimizeBranch(n.body);

        rebuildChildrenFromLists(n, n.condition, n.body);
        return (R) n;
    }

    @Override 
    public R visit(IfNode n) {
        if (n.condition != null) {
            n.condition = (Node) n.condition.accept((Visitor<Object>) this);
        }

        if (n.condition instanceof BooleanNumberNode) {
            boolean isTrue = ((BooleanNumberNode) n.condition).value;
            if (isTrue) {
                List<Node> optimizedThen = optimizeBranch(n.thenBranch);
                //cleaning unused branch
                n.thenBranch = optimizedThen;
                n.elseBranch = null;
                rebuildChildrenFromLists(n, null, n.thenBranch);
                return (R) n;
            } else {
                if (n.elseBranch != null && !n.elseBranch.isEmpty()) {
                    n.thenBranch = null;
                    n.elseBranch = optimizeBranch(n.elseBranch);
                    rebuildChildrenFromLists(n, null, n.elseBranch);
                    return (R) n;
                }
                return null;
            }
        }

        n.thenBranch = optimizeBranch(n.thenBranch);
        n.elseBranch = optimizeBranch(n.elseBranch);
        
        rebuildChildrenFromLists(n, n.condition, n.thenBranch, n.elseBranch);
        return (R) n;
    }
}
