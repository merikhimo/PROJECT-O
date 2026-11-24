package main.java.com.compiler.codegen;

import java.util.*;

public class Locals {
    public static class Var {
        public final int slot;
        public final String type;

        public Var(int slot, String type) {
            this.slot = slot;
            this.type = type;
        }
    }

    private final List<Map<String, Var>> scopes = new ArrayList<>();
    private int next = 1;
    private int max = 1;

    public Locals() {
        push();
    }

    public void push() {
        scopes.add(new HashMap<>());
    }

    public void pop() {
        scopes.removeLast();
    }

    public int declare(String name, String type) {
        int slot = next;
        next += Descriptors.size(type);
        max = Math.max(max, next);
        scopes.getLast().put(name, new Var(slot, type));
        return slot;
    }

    public Var lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Var v = scopes.get(i).get(name);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    public int max() {
        return max;
    }
}
