package main.java.com.compiler.lexer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.java.com.compiler.Parser;

public class Lexer implements Parser.Lexer {
    private final String input;
    private int pos = 0;
    private Object lval;
    private int line = 1;
    private int column = 1;


    private static final Map<String, Integer> KEYWORDS = new HashMap<>();
    static {
        KEYWORDS.put("class", Parser.Lexer.CLASS);
        KEYWORDS.put("extends", Parser.Lexer.EXTENDS);
        KEYWORDS.put("is", Parser.Lexer.IS);
        KEYWORDS.put("end", Parser.Lexer.END);
        KEYWORDS.put("var", Parser.Lexer.VAR);
        KEYWORDS.put("method", Parser.Lexer.METHOD);
        KEYWORDS.put("this", Parser.Lexer.THIS);
        KEYWORDS.put("while", Parser.Lexer.WHILE);
        KEYWORDS.put("loop", Parser.Lexer.LOOP);
        KEYWORDS.put("if", Parser.Lexer.IF);
        KEYWORDS.put("then", Parser.Lexer.THEN);
        KEYWORDS.put("else", Parser.Lexer.ELSE);
        KEYWORDS.put("return", Parser.Lexer.RETURN);
        KEYWORDS.put("null", Parser.Lexer.NULL);
    }

    public Lexer(String input) {
        this.input = input;
    }

    @Override
    public int yylex() throws IOException {
        while (true) {
            skipWhitespace();
            if (pos >= input.length()) {
                return 0;
            }
            char current = input.charAt(pos);

            // Comments
            if (current == '/' && pos + 1 < input.length()) {
                char next = input.charAt(pos + 1);
                if (next == '/') {
                    advanceCursor(2);
                    while (pos < input.length() && input.charAt(pos) != '\n') {
                        advanceCursor(1);
                    }
                    continue;
                } else if (next == '*') {
                    advanceCursor(2);
                    boolean closed = false;
                    while (pos < input.length()) {
                        if (input.charAt(pos) == '*' && pos + 1 < input.length() && input.charAt(pos + 1) == '/') {
                            advanceCursor(2);
                            closed = true;
                            break;
                        }
                        advanceCursor(1);
                    }
                    if (!closed) {
                        throw new IOException("Lexical error: Unterminated block comment at line " + line + ", column " + column);
                    }
                    continue;
                }
            }

            if (current == ':') {
                if (pos + 1 < input.length() && input.charAt(pos + 1) == '=') {
                    advanceCursor(2);
                    return Parser.Lexer.ASSIGN;
                }
                advanceCursor(1);
                return Parser.Lexer.COLON;
            } else if (current == '=') {
                if (pos + 1 < input.length() && input.charAt(pos + 1) == '>') {
                    advanceCursor(2);
                    return Parser.Lexer.ARROW;
                }
                throw new IOException("Lexical error: Single '=' character is not supported at line " + line + ", column " + column + ". Did you mean '=>' or ':='?");
            }

            switch (current) {
                case '(': advanceCursor(1); return Parser.Lexer.BRACKET_L;
                case ')': advanceCursor(1); return Parser.Lexer.BRACKET_R;
                case ',': advanceCursor(1); return Parser.Lexer.COMMA;
                case ';': advanceCursor(1); return Parser.Lexer.SEMICOLON;
                case '.': advanceCursor(1); return Parser.Lexer.DOT;
            }

            if (Character.isDigit(current)) {
                return readNumber();
            }

            if (Character.isLetter(current) || current == '_') {
                return readWord();
            }

            throw new IOException("Lexical error: Unknown character '" + current + "' at line " + line + ", column " + column);
        }
    }

    private int readWord() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length()) {
            char ch = input.charAt(pos);
            if (Character.isLetterOrDigit(ch) || ch == '_') {
                sb.append(ch);
                advanceCursor(1);
            } else {
                break;
            }
        }
        String word = sb.toString();

        if (KEYWORDS.containsKey(word)) {
            this.lval = word;
            return KEYWORDS.get(word);
        }

        if (word.equals("true") || word.equals("false")) {
            this.lval = Boolean.parseBoolean(word);
            return Parser.Lexer.BOOLEAN;
        }

        this.lval = word;
        return Parser.Lexer.IDENTIFIER;
    }

    private int readNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean isReal = false;

        while (pos < input.length()) {
            char ch = input.charAt(pos);

            if (Character.isDigit(ch)) {
                sb.append(ch);
                advanceCursor(1);
            } else if (ch == '.') {
                if (pos + 1 < input.length() && Character.isDigit(input.charAt(pos + 1))) {
                    if (isReal) {
                        throw new IOException("Lexical error: Multiple decimal points in a number at line " + line + ", column " + column);
                    }
                    isReal = true;
                    sb.append('.');
                    advanceCursor(1);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        String numStr = sb.toString();
        if (isReal) {
            this.lval = Double.parseDouble(numStr);
            return Parser.Lexer.REAL;
        } else {
            this.lval = Integer.parseInt(numStr);
            return Parser.Lexer.INT;
        }
    }

    private void advanceCursor(int count) {
        for (int i = 0; i < count; i++) {
            if (pos >= input.length()) break;
            if (input.charAt(pos) == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
            pos++;
        }
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            advanceCursor(1);
        }
    }

    @Override
    public Object getLVal() {
        return this.lval;
    }


    @Override
    public void yyerror(String msg) {
        System.err.println("Syntax Error at line " + line + ", column " + column + ": " + msg);
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
