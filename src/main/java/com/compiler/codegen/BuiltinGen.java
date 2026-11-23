package main.java.com.compiler.codegen;

import java.util.List;

public class BuiltinGen {
    private final CodeGenerator g;

    public BuiltinGen(CodeGenerator g) { this.g = g; }

    /** Получатель и аргументы УЖЕ на стеке. Если в операции есть Real, Integer уже приведён (i2d).
     *  Оставить результат на стеке (для void ничего). */
    public void genMethod(String recvType, String method, List<String> argTypes) {
        throw new UnsupportedOperationException("TODO builtin " + recvType + "." + method);
    }

    /** Integer(5), Real(3), IntArray(10): аргументы уже на стеке, оставить значение. */
    public void genConstruct(String cls, List<String> argTypes) {
        throw new UnsupportedOperationException("TODO builtin constructor " + cls);
    }

    /** Integer.Min, Real.Epsilon и т. п.: положить значение на стек. */
    public void genStaticField(String cls, String member) {
        throw new UnsupportedOperationException("TODO builtin field " + cls + "." + member);
    }
}
