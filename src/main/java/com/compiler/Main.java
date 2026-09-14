package main.java.com.compiler;

import main.java.com.compiler.lexer.Lexer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        String testFilePath = "test/test5.o";
        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(testFilePath)));
            Lexer lexer = new Lexer(sourceCode);
            System.out.println("START LEXING: " + testFilePath);
            while (true) {
                int tokenCode = lexer.yylex();
                String tokenName = getTokenName(tokenCode);
                Object value = lexer.getLVal();
                if (value != null && (tokenCode == Lexer.INT_LITERAL || tokenCode == Lexer.REAL_LITERAL || tokenCode == Lexer.BOOL_LITERAL || tokenCode == Lexer.IDENTIFIER)) {
                    System.out.printf("Token: %-15s | ID: %-2d | Value: %s%n", tokenName, tokenCode, value);
                } else {
                    System.out.printf("Token: %-15s | ID: %-2d%n", tokenName, tokenCode);
                }

                if (tokenCode == Lexer.EOF) {
                    break;
                }
            }

            System.out.println("LEXICAL ANALYSIS COMPLETE");

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static String getTokenName(int code) {
        return switch (code) {
            case Lexer.EOF -> "EOF";
            case Lexer.INT_LITERAL -> "INT_LITERAL";
            case Lexer.REAL_LITERAL -> "REAL_LITERAL";
            case Lexer.BOOL_LITERAL -> "BOOL_LITERAL";
            case Lexer.IDENTIFIER -> "IDENTIFIER";
            case Lexer.CLASS -> "CLASS";
            case Lexer.EXTENDS -> "EXTENDS";
            case Lexer.IS -> "IS";
            case Lexer.END -> "END";
            case Lexer.VAR -> "VAR";
            case Lexer.METHOD -> "METHOD";
            case Lexer.THIS -> "THIS";
            case Lexer.WHILE -> "WHILE";
            case Lexer.LOOP -> "LOOP";
            case Lexer.IF -> "IF";
            case Lexer.THEN -> "THEN";
            case Lexer.ELSE -> "ELSE";
            case Lexer.RETURN -> "RETURN";
            case Lexer.NULL -> "NULL";
            case Lexer.BRACKET_L -> "BRACKET_L";
            case Lexer.BRACKET_R -> "BRACKET_R";
            case Lexer.COMMA -> "COMMA";
            case Lexer.SEMICOLON -> "SEMICOLON";
            case Lexer.DOT -> "DOT";
            case Lexer.COLON -> "COLON";
            case Lexer.ASSIGN -> "ASSIGN";
            case Lexer.ARROW -> "ARROW";
            default -> "UNKNOWN_TOKEN";
        };
    }
}
