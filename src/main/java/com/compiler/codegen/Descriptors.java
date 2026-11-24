package main.java.com.compiler.codegen;

import java.util.List;

public class Descriptors {
    public static String type(String t) {
        return switch (t) {
            case "Integer" -> "I";
            case "Real" -> "D";
            case "Boolean" -> "Z";
            case "IntArray" -> "[I";
            case "RealArray" -> "[D";
            case "BoolArray" -> "[Z";
            case "void" -> "V";
            default -> "L" + t + ";";
        };
    }

    public static String method(List<String> params, String ret) {
        StringBuilder sb = new StringBuilder("(");
        for (String p : params) {
            sb.append(type(p));
        }
        return sb.append(")").append(type(ret)).toString();
    }

    public static int size(String t) {
        if (t.equals("void")) return 0;
        return t.equals("Real") ? 2 : 1;
    }

    public static String prefix(String t) {
        if (t.equals("Real")) {
            return "d";
        }
        if (t.equals("Integer") || t.equals("Boolean")) {
            return "i";
        }
        return "a";
    }
}
