package main.java.com.compiler.codegen;

public class Emitter {
    private final StringBuilder sb = new StringBuilder();
    private int labelCounter = 0;

    public void emit(String instruction) {
        sb.append("    ").append(instruction).append('\n');
    }

    public void directive(String line) {
        sb.append(line).append('\n');

    }

    public void raw(String text) {
        sb.append(text);
    }

    public void blank() {
        sb.append('\n');
    }

    public String newLabel() {
        return "L" + labelCounter++;
    }

    public void label(String name) {
        sb.append(name).append(":\n");
    }

    public String text() {
        return sb.toString();
    }
}
