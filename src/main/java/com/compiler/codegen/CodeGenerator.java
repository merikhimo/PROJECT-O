package main.java.com.compiler.codegen;

import main.java.com.compiler.ast.*;
import main.java.com.compiler.semantic.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CodeGenerator {
    public static final String CLASS_VERSION = "49.0";
    public static final int STACK_LIMIT = 64;

    public final SymbolTable table;
    public Emitter out = new Emitter();
    public Locals locals = new Locals();
    public ClassSymbol currentClass;
    private String currentReturn = "void";

    private final BuiltinGen builtin;
    private final ControlFlowGen flow;

    public CodeGenerator(SymbolTable table) {
        this.table = table;
        this.builtin = new BuiltinGen(this);
        this.flow = new ControlFlowGen(this);
    }

    public static String nameOf(VariableNode v) {
        return v.varName;
    }

    // ================= точка входа =================

    public Map<String, String> generate(Node program) {
        Map<String, String> files = new LinkedHashMap<>();
        for (Node n : program.children) {
            if (n instanceof ClassDeclNode) {
                ClassDeclNode c = (ClassDeclNode) n;
                files.put(c.className, genClass(c));
            }
        }
        return files;
    }

    public void generate(Node program, Path outDir) throws IOException {
        Files.createDirectories(outDir);
        for (Map.Entry<String, String> e : generate(program).entrySet()) {
            Files.write(outDir.resolve(e.getKey() + ".j"), e.getValue().getBytes());
        }
    }

    // ================= классы, конструкторы, методы =================

    private String superOf(ClassDeclNode c) {
        return (c.superClassName != null) ? c.superClassName : "java/lang/Object";
    }

    private String genClass(ClassDeclNode c) {
        currentClass = table.getClass(c.className);
        out = new Emitter();
        if (CLASS_VERSION != null) out.directive(".bytecode " + CLASS_VERSION);
        out.directive(".class public " + c.className);
        out.directive(".super " + superOf(c));
        out.blank();

        for (VarSymbol f : currentClass.fields.values()) {
            out.directive(".field public " + f.name + " " + Descriptors.type(f.typeName));
        }
        out.blank();

        List<Node> members = (c.members == null) ? new ArrayList<Node>() : c.members;
        boolean hasCtor = false;
        for (Node m : members) {
            if (m instanceof ConstructNode) {
                genConstructor(c, (ConstructNode) m);
                hasCtor = true;
            }
        }
        if (!hasCtor) genConstructor(c, null);

        for (Node m : members) {
            if (m instanceof MethodDeclNode) genMethod((MethodDeclNode) m);
        }
        genMain();
        return out.text();
    }

    private void beginMethod(String ret) {
        out = new Emitter();
        locals = new Locals();
        currentReturn = ret;
    }

    private void endMethod(Emitter classOut, String header, int localsCount) {
        String body = out.text();
        out = classOut;
        out.directive(".method " + header);
        out.directive("    .limit stack " + STACK_LIMIT);
        out.directive("    .limit locals " + Math.max(1, localsCount));
        out.raw(body);
        out.directive(".end method");
        out.blank();
    }

    private void genConstructor(ClassDeclNode c, ConstructNode k) {
        List<Node> params = (k == null) ? null : k.parameters;
        List<Node> stmts = (k == null) ? null : k.statements;
        Emitter classOut = out;
        beginMethod("void");

        out.emit("aload_0");
        out.emit("invokespecial " + superOf(c) + "/<init>()V");
        genFieldInits(c);
        declareParams(params);
        genStatements(stmts);
        if (!endsWithReturn(stmts)) out.emit("return");

        endMethod(classOut, "public <init>" + Descriptors.method(paramTypes(params), "void"), locals.max());
    }

    private void genFieldInits(ClassDeclNode c) {
        if (c.members == null) return;
        for (Node m : c.members) {
            if (m instanceof VariableDeclNode) {
                VariableDeclNode v = (VariableDeclNode) m;
                String t = currentClass.fields.get(v.varName).typeName;
                out.emit("aload_0");
                genExpr(v.initializer);
                out.emit("putfield " + c.className + "/" + v.varName + " " + Descriptors.type(t));
            }
        }
    }

    private void genMethod(MethodDeclNode m) {
        if (m.body == null) return;
        MethodHeaderNode h = m.header;
        String ret = (h.returnType == null) ? "void" : h.returnType;
        Emitter classOut = out;
        beginMethod(ret);
        declareParams(h.parameters);

        if (m.body instanceof ExpressionBodyNode) {
            Node e = m.body.children.get(0);
            genExpr(e);
            if (ret.equals("void")) {
                popIfValue(e);
                out.emit("return");
            } else {
                out.emit(returnOp(ret));
            }
        } else {
            List<Node> stmts = m.body.children;
            genStatements(stmts);
            if (!endsWithReturn(stmts)) emitDefaultReturn(ret);
        }
        endMethod(classOut, "public " + h.methodName + Descriptors.method(paramTypes(h.parameters), ret),
                locals.max());
    }

    private void declareParams(List<Node> params) {
        if (params == null) return;
        for (Node p : params) {
            ParameterNode pn = (ParameterNode) p;
            locals.declare(pn.paramName, pn.paramType);
        }
    }

    private List<String> paramTypes(List<Node> params) {
        List<String> types = new ArrayList<>();
        if (params != null) {
            for (Node p : params) types.add(((ParameterNode) p).paramType);
        }
        return types;
    }

    private boolean endsWithReturn(List<Node> stmts) {
        return stmts != null && !stmts.isEmpty() && stmts.get(stmts.size() - 1) instanceof ReturnNode;
    }

    private void emitDefaultReturn(String ret) {
        if (ret.equals("void")) { out.emit("return"); return; }
        switch (Descriptors.prefix(ret)) {
            case "i": out.emit("iconst_0"); break;
            case "d": out.emit("dconst_0"); break;
            default:  out.emit("aconst_null"); break;
        }
        out.emit(returnOp(ret));
    }

    private String returnOp(String t) {
        return Descriptors.prefix(t) + "return";
    }

    // ================= main: точка входа программы =================

    /** В каждом классе: создаёт объект первым конструктором, аргументы берёт из командной строки. */
    private void genMain() {
        Emitter classOut = out;
        out = new Emitter();
        MethodSymbol ctor = currentClass.constructors.get(0);
        List<String> pts = ctor.paramTypes;
        int nextSlot = 1;

        out.emit("new " + currentClass.name);
        out.emit("dup");
        for (int i = 0; i < pts.size(); i++) {
            switch (pts.get(i)) {
                case "Integer":
                    argScalar(i, "java/lang/Integer/parseInt(Ljava/lang/String;)I"); break;
                case "Real":
                    argScalar(i, "java/lang/Double/parseDouble(Ljava/lang/String;)D"); break;
                case "Boolean":
                    argScalar(i, "java/lang/Boolean/parseBoolean(Ljava/lang/String;)Z"); break;
                case "IntArray":
                    argArray(nextSlot++, "int", "java/lang/Integer/parseInt(Ljava/lang/String;)I", "iastore"); break;
                case "RealArray":
                    argArray(nextSlot++, "double", "java/lang/Double/parseDouble(Ljava/lang/String;)D", "dastore"); break;
                case "BoolArray":
                    argArray(nextSlot++, "boolean", "java/lang/Boolean/parseBoolean(Ljava/lang/String;)Z", "bastore"); break;
                default:
                    out.emit("aconst_null");
            }
        }
        out.emit("invokespecial " + currentClass.name + "/<init>" + Descriptors.method(pts, "void"));
        out.emit("pop");
        out.emit("return");
        endMethod(classOut, "public static main([Ljava/lang/String;)V", nextSlot);
    }

    private void argScalar(int index, String parse) {
        out.emit("aload_0");
        out.emit("ldc " + index);
        out.emit("aaload");
        out.emit("invokestatic " + parse);
    }

    private void argArray(int idxSlot, String elemType, String parse, String storeOp) {
        String loop = out.newLabel(), done = out.newLabel();
        out.emit("aload_0");
        out.emit("arraylength");
        out.emit("newarray " + elemType);
        out.emit("iconst_0");
        out.emit("istore " + idxSlot);
        out.label(loop);
        out.emit("iload " + idxSlot);
        out.emit("aload_0");
        out.emit("arraylength");
        out.emit("if_icmpge " + done);
        out.emit("dup");
        out.emit("iload " + idxSlot);
        out.emit("aload_0");
        out.emit("iload " + idxSlot);
        out.emit("aaload");
        out.emit("invokestatic " + parse);
        out.emit(storeOp);
        out.emit("iinc " + idxSlot + " 1");
        out.emit("goto " + loop);
        out.label(done);
    }

    // ================= операторы =================

    private void genStatements(List<Node> stmts) {
        if (stmts == null) return;
        for (Node s : stmts) genStmt(s);
    }

    public void genBlock(List<Node> stmts) {
        if (stmts == null) return;
        locals.push();
        for (Node s : stmts) genStmt(s);
        locals.pop();
    }

    public void genStmt(Node n) {
        if (n instanceof VariableDeclNode)      genLocalDecl((VariableDeclNode) n);
        else if (n instanceof AssignmentNode)   genAssign((AssignmentNode) n);
        else if (n instanceof ReturnNode)       genReturn((ReturnNode) n);
        else if (n instanceof IfNode)           flow.genIf((IfNode) n);
        else if (n instanceof WhileNode)        flow.genWhile((WhileNode) n);
        else {
            genExpr(n);
            popIfValue(n);
        }
    }

    private void popIfValue(Node e) {
        String t = e.resolvedType;
        if (t != null && !t.equals("void")) out.emit(Descriptors.size(t) == 2 ? "pop2" : "pop");
    }

    private void genLocalDecl(VariableDeclNode n) {
        genExpr(n.initializer);
        String t = (n.resolvedType != null) ? n.resolvedType : n.initializer.resolvedType;
        int slot = locals.declare(n.varName, t);
        out.emit(Descriptors.prefix(t) + "store " + slot);
    }

    private void genAssign(AssignmentNode n) {
        Locals.Var v = locals.lookup(n.target);
        if (v != null) {
            genExpr(n.expression);
            out.emit(Descriptors.prefix(v.type) + "store " + v.slot);
        } else {
            VarSymbol f = table.findField(currentClass.name, n.target);
            out.emit("aload_0");
            genExpr(n.expression);
            out.emit("putfield " + currentClass.name + "/" + n.target + " " + Descriptors.type(f.typeName));
        }
    }

    private void genReturn(ReturnNode n) {
        if (n.children.isEmpty()) {
            out.emit("return");
        } else {
            genExpr(n.children.get(0));
            out.emit(returnOp(currentReturn));
        }
    }

    // ================= выражения =================

    /** Оставляет значение выражения на стеке (для void-вызова ничего). */
    public void genExpr(Node n) {
        if (n instanceof NumberNode)             out.emit("ldc " + ((NumberNode) n).value);
        else if (n instanceof RealNumberNode)    out.emit("ldc2_w " + ((RealNumberNode) n).value);
        else if (n instanceof BooleanNumberNode) out.emit(((BooleanNumberNode) n).value ? "iconst_1" : "iconst_0");
        else if (n instanceof ThisNode)          out.emit("aload_0");
        else if (n instanceof VariableNode)      genVariable((VariableNode) n);
        else if (n instanceof MemberAccessNode)  genMemberAccess((MemberAccessNode) n);
        else if (n instanceof FunctionCallNode)  genCall((FunctionCallNode) n);
        else throw new IllegalStateException("codegen: unsupported expression " + n.type);
    }

    private void genVariable(VariableNode n) {
        String name = nameOf(n);
        Locals.Var v = locals.lookup(name);
        if (v != null) {
            out.emit(Descriptors.prefix(v.type) + "load " + v.slot);
            return;
        }
        VarSymbol f = table.findField(currentClass.name, name);
        if (f == null) throw new IllegalStateException("codegen: unknown variable " + name);
        out.emit("aload_0");
        out.emit("getfield " + currentClass.name + "/" + name + " " + Descriptors.type(f.typeName));
    }

    public boolean isClassRef(Node n) {
        if (!(n instanceof VariableNode)) return false;
        String name = nameOf((VariableNode) n);
        return locals.lookup(name) == null
                && table.findField(currentClass.name, name) == null
                && table.classes.containsKey(name);
    }

    private void genMemberAccess(MemberAccessNode n) {
        String member = nameOf((VariableNode) n.right);
        if (isClassRef(n.left)) {
            builtin.genStaticField(nameOf((VariableNode) n.left), member);
            return;
        }
        String rt = n.left.resolvedType;
        ClassSymbol rc = table.getClass(rt);
        genExpr(n.left);
        VarSymbol f = table.findField(rt, member);
        List<String> noArgs = new ArrayList<>();
        if (f != null && !rc.builtin) {
            out.emit("getfield " + rt + "/" + member + " " + Descriptors.type(f.typeName));
        } else if (f != null) {                          // x.Min у встроенного: объект не нужен
            out.emit(Descriptors.size(rt) == 2 ? "pop2" : "pop");
            builtin.genStaticField(rt, member);
        } else if (rc.builtin) {
            builtin.genMethod(rt, member, noArgs);
        } else {
            MethodSymbol m = table.findMethod(rt, member, noArgs);
            out.emit("invokevirtual " + rt + "/" + member + Descriptors.method(m.paramTypes, m.returnType));
        }
    }

    private void genCall(FunctionCallNode n) {
        Node callee = n.expression;
        List<Node> args = (n.arguments == null) ? new ArrayList<Node>() : n.arguments;
        if (callee instanceof VariableNode) {
            String name = nameOf((VariableNode) callee);
            if (name.equals("Print")) genPrint(args.get(0));
            else genConstructorCall(name, args);
        } else if (callee instanceof MemberAccessNode) {
            genMethodCall((MemberAccessNode) callee, args);
        } else {
            throw new IllegalStateException("codegen: unsupported call target " + callee.type);
        }
    }

    private void genPrint(Node arg) {
        out.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
        genExpr(arg);
        String t = arg.resolvedType;
        String d = t.equals("Integer") ? "I" : t.equals("Real") ? "D" : t.equals("Boolean") ? "Z"
                : "Ljava/lang/Object;";
        out.emit("invokevirtual java/io/PrintStream/println(" + d + ")V");
    }

    private void genConstructorCall(String cls, List<Node> args) {
        List<String> argTypes = typesOf(args);
        if (table.getClass(cls).builtin) {
            for (Node a : args) genExpr(a);
            builtin.genConstruct(cls, argTypes);
            return;
        }
        out.emit("new " + cls);
        out.emit("dup");
        for (Node a : args) genExpr(a);
        MethodSymbol ctor = table.findConstructor(cls, argTypes);
        if (ctor == null) throw new IllegalStateException("codegen: no constructor " + cls + argTypes);
        out.emit("invokespecial " + cls + "/<init>" + Descriptors.method(ctor.paramTypes, "void"));
    }

    private void genMethodCall(MemberAccessNode ma, List<Node> args) {
        String method = nameOf((VariableNode) ma.right);
        Node recv = ma.left;
        String rt = recv.resolvedType;
        List<String> argTypes = typesOf(args);

        if (table.getClass(rt).builtin) {
            boolean numeric = rt.equals("Integer") || rt.equals("Real");
            boolean promote = numeric && (rt.equals("Real") || argTypes.contains("Real"));
            genExpr(recv);
            if (promote && rt.equals("Integer")) out.emit("i2d");
            for (int i = 0; i < args.size(); i++) {
                genExpr(args.get(i));
                if (promote && argTypes.get(i).equals("Integer")) out.emit("i2d");
            }
            builtin.genMethod(rt, method, argTypes);
        } else {
            genExpr(recv);
            for (Node a : args) genExpr(a);
            MethodSymbol m = table.findMethod(rt, method, argTypes);
            if (m == null) throw new IllegalStateException("codegen: no method " + rt + "." + method + argTypes);
            out.emit("invokevirtual " + rt + "/" + method + Descriptors.method(m.paramTypes, m.returnType));
        }
    }

    private List<String> typesOf(List<Node> nodes) {
        List<String> t = new ArrayList<>();
        for (Node a : nodes) t.add(a.resolvedType);
        return t;
    }
}