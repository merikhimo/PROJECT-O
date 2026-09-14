package main.java.com.compiler.lexer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    private final String input;
    private int pos = 0;
    private Object lval;
    private int line = 1;
    private int column = 1;

    public static final int EOF = 0;
    public static final int INT_LITERAL    = 1;
    public static final int REAL_LITERAL   = 2;
    public static final int BOOL_LITERAL   = 3;
    public static final int IDENTIFIER     = 4;
    public static final int CLASS          = 5;
    public static final int EXTENDS        = 6;
    public static final int IS             = 7;
    public static final int END            = 8;
    public static final int VAR            = 9;
    public static final int METHOD         = 10;
    public static final int THIS           = 11;
    public static final int WHILE          = 12;
    public static final int LOOP           = 13;
    public static final int IF             = 14;
    public static final int THEN           = 15;
    public static final int ELSE           = 16;
    public static final int RETURN         = 17;
    public static final int NULL           = 18;
    public static final int BRACKET_L      = 19;
    public static final int BRACKET_R      = 20;
    public static final int COMMA          = 21;
    public static final int SEMICOLON      = 22;  //;
    public static final int DOT            = 23;
    public static final int COLON          = 24;  //:
    public static final int ASSIGN         = 25;  // :=
    public static final int ARROW          = 26;  // =>

    private static final Map<String, Integer> KEYWORDS = new HashMap<>();
    static {
        KEYWORDS.put("class", CLASS);
        KEYWORDS.put("extends", EXTENDS);
        KEYWORDS.put("is", IS);
        KEYWORDS.put("end", END);
        KEYWORDS.put("var", VAR);
        KEYWORDS.put("method", METHOD);
        KEYWORDS.put("this", THIS);
        KEYWORDS.put("while", WHILE);
        KEYWORDS.put("loop", LOOP);
        KEYWORDS.put("if", IF);
        KEYWORDS.put("then", THEN);
        KEYWORDS.put("else", ELSE);
        KEYWORDS.put("return", RETURN);
        KEYWORDS.put("null", NULL);
    }

    public Lexer(String input) {
        this.input = input;
    }

    public int yylex() throws IOException {
        while (true) {
            skipWhitespace();
            if (pos >= input.length()) {
                return EOF;
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
                    return ASSIGN;
                }
                advanceCursor(1);
                return COLON;
            } else  if (current == '=') {
                if (pos + 1 < input.length() && input.charAt(pos + 1) == '>') {
                    advanceCursor(2);
                    return ARROW;
                }
                throw new IOException("Lexical error: Single '=' character is not supported at line " + line + ", column " + column + ". Did you mean '=>' or ':='?");
            }

            switch (current) {
                case '(': advanceCursor(1); return BRACKET_L;
                case ')': advanceCursor(1); return BRACKET_R;
                case ',': advanceCursor(1); return COMMA;
                case ';': advanceCursor(1); return SEMICOLON;
                case '.': advanceCursor(1); return DOT;
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
            return BOOL_LITERAL;
        }

        this.lval = word;
        return IDENTIFIER;
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
            return REAL_LITERAL;
        } else {
            this.lval = Integer.parseInt(numStr);
            return INT_LITERAL;
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

    public Object getLVal() {
        return this.lval;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}