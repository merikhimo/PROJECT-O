package main.java.com.compiler.codegen;

import java.util.List;

public class BuiltinGen {
    private final CodeGenerator g;

    public BuiltinGen(CodeGenerator g) {
        this.g = g;
    }

    private void emit(String instruction) {
        g.out.emit(instruction);
    }

    private IllegalStateException unsupported(String what) {
        return new IllegalStateException("codegen: unsupported builtin " + what);
    }

    public void genMethod(String recvType, String method, List<String> argTypes) {
        switch (recvType) {
            case "Integer":
            case "Real":
                genNumeric(recvType, method, argTypes);
                return;
            case "Boolean":
                genBoolean(method);
                return;
            case "IntArray":
            case "RealArray":
            case "BoolArray":
                genArray(recvType, method);
                return;
            default:
                throw unsupported(recvType + "." + method);
        }
    }

    private void genNumeric(String recv, String method, List<String> argTypes) {
        boolean real = recv.equals("Real") || argTypes.contains("Real");

        switch (method) {
            case "Plus": emit(real ? "dadd" : "iadd"); return;
            case "Minus": emit(real ? "dsub" : "isub"); return;
            case "Mult": emit(real ? "dmul" : "imul"); return;
            case "Div": emit(real ? "ddiv" : "idiv"); return;
            case "Rem": emit(real ? "drem" : "irem"); return;
            case "UnaryMinus": emit(real ? "dneg" : "ineg"); return;

            case "Less": compare(real, "lt"); return;
            case "LessEqual": compare(real, "le"); return;
            case "Greater": compare(real, "gt"); return;
            case "GreaterEqual": compare(real, "ge"); return;
            case "Equal": compare(real, "eq"); return;

            case "toReal": emit("i2d"); return;
            case "toInteger": emit("d2i"); return;
            case "toBoolean": pushBool("ifne"); return;
            default:
                throw unsupported(recv + "." + method);
        }
    }

    private void genBoolean(String method) {
        switch (method) {
            case "And": emit("iand"); return;
            case "Or": emit("ior"); return;
            case "Xor": emit("ixor"); return;
            case "Not": emit("iconst_1"); emit("ixor"); return;
            case "toInteger": return;
            default:
                throw unsupported("Boolean." + method);
        }
    }

    private void genArray(String recv, String method) {
        String p = recv.equals("IntArray") ? "i" : recv.equals("RealArray") ? "d" : "b";
        switch (method) {
            case "Length": emit("arraylength"); return;
            case "get": emit(p + "aload"); return;
            case "set": emit(p + "astore"); return;
            default:
                throw unsupported(recv + "." + method);
        }
    }

    private void compare(boolean real, String cond) {
        if (real) {
            emit(cond.equals("lt") || cond.equals("le") ? "dcmpg" : "dcmpl");
            pushBool("if" + cond);
        } else {
            pushBool("if_icmp" + cond);
        }
    }

    private void pushBool(String jumpIfTrue) {
        String yes = g.out.newLabel();
        String done = g.out.newLabel();
        emit(jumpIfTrue + " " + yes);
        emit("iconst_0");
        emit("goto " + done);
        g.out.label(yes);
        emit("iconst_1");
        g.out.label(done);
    }

    public void genConstruct(String cls, List<String> argTypes) {
        switch (cls) {
            case "Integer":
                if (argTypes.getFirst().equals("Real")) emit("d2i");
                return;
            case "Real":
                if (argTypes.getFirst().equals("Integer")) emit("i2d");
                return;
            case "Boolean":
                return;
            case "IntArray":
                emit("newarray int");
                return;
            case "RealArray":
                emit("newarray double");
                return;
            case "BoolArray":
                emit("newarray boolean");
                return;
            default:
                throw unsupported("constructor " + cls);
        }
    }

    public void genStaticField(String cls, String member) {
        if (cls.equals("Integer")) {
            if (member.equals("Min")) {
                emit("ldc " + Integer.MIN_VALUE);
                return;
            }
            if (member.equals("Max")) {
                emit("ldc " + Integer.MAX_VALUE);
                return;
            }
        } else if (cls.equals("Real")) {
            switch (member) {
                case "Min" -> {
                    emit("ldc2_w " + (-Double.MAX_VALUE));
                    return;
                }
                case "Max" -> {
                    emit("ldc2_w " + Double.MAX_VALUE);
                    return;
                }
                case "Epsilon" -> {
                    emit("ldc2_w " + Math.ulp(1.0));
                    return;
                }
            }
        }
        throw unsupported("field " + cls + "." + member);
    }
}
