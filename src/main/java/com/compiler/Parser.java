/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package main.java.com.compiler;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "src/main/bison/parser.y":5  */

    import main.java.com.compiler.ast.*;
    import java.util.List;
    import java.util.ArrayList;

/* "src/main/java/com/compiler/Parser.java":51  */

/**
 * A Bison parser, automatically generated from <tt>src/main/bison/parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Parser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";






  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_INT(3),                      /* INT  */
    S_REAL(4),                     /* REAL  */
    S_BOOLEAN(5),                  /* BOOLEAN  */
    S_IDENTIFIER(6),               /* IDENTIFIER  */
    S_CLASS(7),                    /* CLASS  */
    S_EXTENDS(8),                  /* EXTENDS  */
    S_IS(9),                       /* IS  */
    S_END(10),                     /* END  */
    S_VAR(11),                     /* VAR  */
    S_METHOD(12),                  /* METHOD  */
    S_THIS(13),                    /* THIS  */
    S_WHILE(14),                   /* WHILE  */
    S_LOOP(15),                    /* LOOP  */
    S_IF(16),                      /* IF  */
    S_THEN(17),                    /* THEN  */
    S_ELSE(18),                    /* ELSE  */
    S_RETURN(19),                  /* RETURN  */
    S_NULL(20),                    /* NULL  */
    S_BRACKET_L(21),               /* BRACKET_L  */
    S_BRACKET_R(22),               /* BRACKET_R  */
    S_COMMA(23),                   /* COMMA  */
    S_SEMICOLON(24),               /* SEMICOLON  */
    S_DOT(25),                     /* DOT  */
    S_COLON(26),                   /* COLON  */
    S_ASSIGN(27),                  /* ASSIGN  */
    S_ARROW(28),                   /* ARROW  */
    S_YYACCEPT(29),                /* $accept  */
    S_program(30),                 /* program  */
    S_class_declaration_list(31),  /* class_declaration_list  */
    S_class_declaration(32),       /* class_declaration  */
    S_member_list(33),             /* member_list  */
    S_member(34),                  /* member  */
    S_variable_declaration(35),    /* variable_declaration  */
    S_method_declaration(36),      /* method_declaration  */
    S_method_header(37),           /* method_header  */
    S_method_body(38),             /* method_body  */
    S_parameter_list(39),          /* parameter_list  */
    S_parameter(40),               /* parameter  */
    S_constructor_declaration(41), /* constructor_declaration  */
    S_statement_list(42),          /* statement_list  */
    S_statement(43),               /* statement  */
    S_assignment(44),              /* assignment  */
    S_while_loop(45),              /* while_loop  */
    S_if_statement(46),            /* if_statement  */
    S_return_statement(47),        /* return_statement  */
    S_expression(48),              /* expression  */
    S_postfix_expression(49),      /* postfix_expression  */
    S_primary(50),                 /* primary  */
    S_function_call(51),           /* function_call  */
    S_argument_list(52);           /* argument_list  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_INT,
      SymbolKind.S_REAL,
      SymbolKind.S_BOOLEAN,
      SymbolKind.S_IDENTIFIER,
      SymbolKind.S_CLASS,
      SymbolKind.S_EXTENDS,
      SymbolKind.S_IS,
      SymbolKind.S_END,
      SymbolKind.S_VAR,
      SymbolKind.S_METHOD,
      SymbolKind.S_THIS,
      SymbolKind.S_WHILE,
      SymbolKind.S_LOOP,
      SymbolKind.S_IF,
      SymbolKind.S_THEN,
      SymbolKind.S_ELSE,
      SymbolKind.S_RETURN,
      SymbolKind.S_NULL,
      SymbolKind.S_BRACKET_L,
      SymbolKind.S_BRACKET_R,
      SymbolKind.S_COMMA,
      SymbolKind.S_SEMICOLON,
      SymbolKind.S_DOT,
      SymbolKind.S_COLON,
      SymbolKind.S_ASSIGN,
      SymbolKind.S_ARROW,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_program,
      SymbolKind.S_class_declaration_list,
      SymbolKind.S_class_declaration,
      SymbolKind.S_member_list,
      SymbolKind.S_member,
      SymbolKind.S_variable_declaration,
      SymbolKind.S_method_declaration,
      SymbolKind.S_method_header,
      SymbolKind.S_method_body,
      SymbolKind.S_parameter_list,
      SymbolKind.S_parameter,
      SymbolKind.S_constructor_declaration,
      SymbolKind.S_statement_list,
      SymbolKind.S_statement,
      SymbolKind.S_assignment,
      SymbolKind.S_while_loop,
      SymbolKind.S_if_statement,
      SymbolKind.S_return_statement,
      SymbolKind.S_expression,
      SymbolKind.S_postfix_expression,
      SymbolKind.S_primary,
      SymbolKind.S_function_call,
      SymbolKind.S_argument_list
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* Return YYSTR after stripping away unnecessary quotes and
       backslashes, so that it's suitable for yyerror.  The heuristic is
       that double-quoting is unnecessary unless the string contains an
       apostrophe, a comma, or backslash (other than backslash-backslash).
       YYSTR is taken from yytname.  */
    private static String yytnamerr_(String yystr)
    {
      if (yystr.charAt (0) == '"')
        {
          StringBuffer yyr = new StringBuffer();
          strip_quotes: for (int i = 1; i < yystr.length(); i++)
            switch (yystr.charAt(i))
              {
              case '\'':
              case ',':
                break strip_quotes;

              case '\\':
                if (yystr.charAt(++i) != '\\')
                  break strip_quotes;
                /* Fall through.  */
              default:
                yyr.append(yystr.charAt(i));
                break;

              case '"':
                return yyr.toString();
              }
        }
      return yystr;
    }

    /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yytname_ = yytname_init();
  private static final String[] yytname_init()
  {
    return new String[]
    {
  "\"end of file\"", "error", "\"invalid token\"", "INT", "REAL",
  "BOOLEAN", "IDENTIFIER", "CLASS", "EXTENDS", "IS", "END", "VAR",
  "METHOD", "THIS", "WHILE", "LOOP", "IF", "THEN", "ELSE", "RETURN",
  "NULL", "BRACKET_L", "BRACKET_R", "COMMA", "SEMICOLON", "DOT", "COLON",
  "ASSIGN", "ARROW", "$accept", "program", "class_declaration_list",
  "class_declaration", "member_list", "member", "variable_declaration",
  "method_declaration", "method_header", "method_body", "parameter_list",
  "parameter", "constructor_declaration", "statement_list", "statement",
  "assignment", "while_loop", "if_statement", "return_statement",
  "expression", "postfix_expression", "primary", "function_call",
  "argument_list", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yytnamerr_(yytname_[yycode_]);
    }

  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token INT, to be returned by the scanner.  */
    static final int INT = 1;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 2;
    /** Token BOOLEAN, to be returned by the scanner.  */
    static final int BOOLEAN = 3;
    /** Token IDENTIFIER, to be returned by the scanner.  */
    static final int IDENTIFIER = 4;
    /** Token CLASS, to be returned by the scanner.  */
    static final int CLASS = 5;
    /** Token EXTENDS, to be returned by the scanner.  */
    static final int EXTENDS = 6;
    /** Token IS, to be returned by the scanner.  */
    static final int IS = 7;
    /** Token END, to be returned by the scanner.  */
    static final int END = 8;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 9;
    /** Token METHOD, to be returned by the scanner.  */
    static final int METHOD = 10;
    /** Token THIS, to be returned by the scanner.  */
    static final int THIS = 11;
    /** Token WHILE, to be returned by the scanner.  */
    static final int WHILE = 12;
    /** Token LOOP, to be returned by the scanner.  */
    static final int LOOP = 13;
    /** Token IF, to be returned by the scanner.  */
    static final int IF = 14;
    /** Token THEN, to be returned by the scanner.  */
    static final int THEN = 15;
    /** Token ELSE, to be returned by the scanner.  */
    static final int ELSE = 16;
    /** Token RETURN, to be returned by the scanner.  */
    static final int RETURN = 17;
    /** Token NULL, to be returned by the scanner.  */
    static final int NULL = 18;
    /** Token BRACKET_L, to be returned by the scanner.  */
    static final int BRACKET_L = 19;
    /** Token BRACKET_R, to be returned by the scanner.  */
    static final int BRACKET_R = 20;
    /** Token COMMA, to be returned by the scanner.  */
    static final int COMMA = 21;
    /** Token SEMICOLON, to be returned by the scanner.  */
    static final int SEMICOLON = 22;
    /** Token DOT, to be returned by the scanner.  */
    static final int DOT = 23;
    /** Token COLON, to be returned by the scanner.  */
    static final int COLON = 24;
    /** Token ASSIGN, to be returned by the scanner.  */
    static final int ASSIGN = 25;
    /** Token ARROW, to be returned by the scanner.  */
    static final int ARROW = 26;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer)
  {

    this.yylexer = yylexer;

  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Object value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Object[] newValueStack = new Object[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Object valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 2: /* program: class_declaration_list  */
  if (yyn == 2)
    /* "src/main/bison/parser.y":78  */
    {
        rootNode = new ProgramNode(((List<Node>)(yystack.valueAt (0))));
        yyval = rootNode;
    };
  break;


  case 3: /* class_declaration_list: %empty  */
  if (yyn == 3)
    /* "src/main/bison/parser.y":86  */
    {
        yyval = new ArrayList<Node>();
    };
  break;


  case 4: /* class_declaration_list: class_declaration_list class_declaration  */
  if (yyn == 4)
    /* "src/main/bison/parser.y":90  */
    {
        ((List<Node>)(yystack.valueAt (1))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (1)));
    };
  break;


  case 5: /* class_declaration: CLASS IDENTIFIER IS member_list END  */
  if (yyn == 5)
    /* "src/main/bison/parser.y":98  */
    {
        yyval = new ClassDeclNode(((String)(yystack.valueAt (3))), null, ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 6: /* class_declaration: CLASS IDENTIFIER EXTENDS IDENTIFIER IS member_list END  */
  if (yyn == 6)
    /* "src/main/bison/parser.y":102  */
    {
        yyval = new ClassDeclNode(((String)(yystack.valueAt (5))), ((String)(yystack.valueAt (3))), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 7: /* member_list: %empty  */
  if (yyn == 7)
    /* "src/main/bison/parser.y":109  */
    {
        yyval = new ArrayList<Node>();
    };
  break;


  case 8: /* member_list: member_list member  */
  if (yyn == 8)
    /* "src/main/bison/parser.y":113  */
    {
        ((List<Node>)(yystack.valueAt (1))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (1)));
    };
  break;


  case 9: /* member: variable_declaration  */
  if (yyn == 9)
    /* "src/main/bison/parser.y":120  */
                         { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 10: /* member: method_declaration  */
  if (yyn == 10)
    /* "src/main/bison/parser.y":121  */
                         { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 11: /* member: constructor_declaration  */
  if (yyn == 11)
    /* "src/main/bison/parser.y":122  */
                            { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 12: /* variable_declaration: VAR IDENTIFIER COLON expression  */
  if (yyn == 12)
    /* "src/main/bison/parser.y":127  */
    {
        yyval = new VariableDeclNode(((String)(yystack.valueAt (2))), ((Node)(yystack.valueAt (0))));
    };
  break;


  case 13: /* method_declaration: method_header  */
  if (yyn == 13)
    /* "src/main/bison/parser.y":134  */
    {
        yyval = new MethodDeclNode(((Node)(yystack.valueAt (0))), null);
    };
  break;


  case 14: /* method_declaration: method_header method_body  */
  if (yyn == 14)
    /* "src/main/bison/parser.y":138  */
    {
        yyval = new MethodDeclNode(((Node)(yystack.valueAt (1))), ((Node)(yystack.valueAt (0))));
    };
  break;


  case 15: /* method_header: METHOD IDENTIFIER  */
  if (yyn == 15)
    /* "src/main/bison/parser.y":145  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (0))), new ArrayList<Node>(), null);
    };
  break;


  case 16: /* method_header: METHOD IDENTIFIER BRACKET_L BRACKET_R  */
  if (yyn == 16)
    /* "src/main/bison/parser.y":149  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (2))), new ArrayList<Node>(), null);
    };
  break;


  case 17: /* method_header: METHOD IDENTIFIER BRACKET_L parameter_list BRACKET_R  */
  if (yyn == 17)
    /* "src/main/bison/parser.y":153  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (3))), ((List<Node>)(yystack.valueAt (1))), null);
    };
  break;


  case 18: /* method_header: METHOD IDENTIFIER COLON IDENTIFIER  */
  if (yyn == 18)
    /* "src/main/bison/parser.y":157  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (2))), new ArrayList<Node>(), ((String)(yystack.valueAt (0))));
    };
  break;


  case 19: /* method_header: METHOD IDENTIFIER BRACKET_L BRACKET_R COLON IDENTIFIER  */
  if (yyn == 19)
    /* "src/main/bison/parser.y":161  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (4))), new ArrayList<Node>(), ((String)(yystack.valueAt (0))));
    };
  break;


  case 20: /* method_header: METHOD IDENTIFIER BRACKET_L parameter_list BRACKET_R COLON IDENTIFIER  */
  if (yyn == 20)
    /* "src/main/bison/parser.y":165  */
    {
        yyval = new MethodHeaderNode(((String)(yystack.valueAt (5))), ((List<Node>)(yystack.valueAt (3))), ((String)(yystack.valueAt (0))));
    };
  break;


  case 21: /* method_body: IS statement_list END  */
  if (yyn == 21)
    /* "src/main/bison/parser.y":172  */
    {
        yyval = new MethodBodyNode(((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 22: /* method_body: ARROW expression  */
  if (yyn == 22)
    /* "src/main/bison/parser.y":176  */
    {
        yyval = new ExpressionBodyNode(((Node)(yystack.valueAt (0))));
    };
  break;


  case 23: /* parameter_list: parameter  */
  if (yyn == 23)
    /* "src/main/bison/parser.y":183  */
    {
        List<Node> list = new ArrayList<Node>();
        list.add(((Node)(yystack.valueAt (0))));
        yyval = list;
    };
  break;


  case 24: /* parameter_list: parameter_list COMMA parameter  */
  if (yyn == 24)
    /* "src/main/bison/parser.y":189  */
    {
        ((List<Node>)(yystack.valueAt (2))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (2)));
    };
  break;


  case 25: /* parameter: IDENTIFIER COLON IDENTIFIER  */
  if (yyn == 25)
    /* "src/main/bison/parser.y":197  */
    {
        yyval = new ParameterNode(((String)(yystack.valueAt (2))), ((String)(yystack.valueAt (0))));
    };
  break;


  case 26: /* constructor_declaration: THIS IS statement_list END  */
  if (yyn == 26)
    /* "src/main/bison/parser.y":204  */
    {
        yyval = new ConstructNode(new ArrayList<Node>(), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 27: /* constructor_declaration: THIS BRACKET_L BRACKET_R IS statement_list END  */
  if (yyn == 27)
    /* "src/main/bison/parser.y":208  */
    {
        yyval = new ConstructNode(new ArrayList<Node>(), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 28: /* constructor_declaration: THIS BRACKET_L parameter_list BRACKET_R IS statement_list END  */
  if (yyn == 28)
    /* "src/main/bison/parser.y":212  */
    {
        yyval = new ConstructNode(((List<Node>)(yystack.valueAt (4))), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 29: /* statement_list: %empty  */
  if (yyn == 29)
    /* "src/main/bison/parser.y":219  */
    {
        yyval = new ArrayList<Node>();
    };
  break;


  case 30: /* statement_list: statement_list statement  */
  if (yyn == 30)
    /* "src/main/bison/parser.y":223  */
    {
        ((List<Node>)(yystack.valueAt (1))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (1)));
    };
  break;


  case 31: /* statement_list: statement_list variable_declaration  */
  if (yyn == 31)
    /* "src/main/bison/parser.y":228  */
    {
        ((List<Node>)(yystack.valueAt (1))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (1)));
    };
  break;


  case 32: /* statement: assignment  */
  if (yyn == 32)
    /* "src/main/bison/parser.y":235  */
                     { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 33: /* statement: while_loop  */
  if (yyn == 33)
    /* "src/main/bison/parser.y":236  */
                     { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 34: /* statement: if_statement  */
  if (yyn == 34)
    /* "src/main/bison/parser.y":237  */
                     { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 35: /* statement: return_statement  */
  if (yyn == 35)
    /* "src/main/bison/parser.y":238  */
                     { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 36: /* statement: expression  */
  if (yyn == 36)
    /* "src/main/bison/parser.y":239  */
                     { yyval = ((Node)(yystack.valueAt (0))); };
  break;


  case 37: /* assignment: IDENTIFIER ASSIGN expression  */
  if (yyn == 37)
    /* "src/main/bison/parser.y":244  */
    {
        yyval = new AssignmentNode(((String)(yystack.valueAt (2))), ((Node)(yystack.valueAt (0))));
    };
  break;


  case 38: /* while_loop: WHILE expression LOOP statement_list END  */
  if (yyn == 38)
    /* "src/main/bison/parser.y":251  */
    {
        yyval = new WhileNode(((Node)(yystack.valueAt (3))), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 39: /* if_statement: IF expression THEN statement_list END  */
  if (yyn == 39)
    /* "src/main/bison/parser.y":258  */
    {
        yyval = new IfNode(((Node)(yystack.valueAt (3))), ((List<Node>)(yystack.valueAt (1))), new ArrayList<Node>());
    };
  break;


  case 40: /* if_statement: IF expression THEN statement_list ELSE statement_list END  */
  if (yyn == 40)
    /* "src/main/bison/parser.y":262  */
    {
        yyval = new IfNode(((Node)(yystack.valueAt (5))), ((List<Node>)(yystack.valueAt (3))), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 41: /* return_statement: RETURN  */
  if (yyn == 41)
    /* "src/main/bison/parser.y":269  */
    {
        yyval = new ReturnNode(null);
    };
  break;


  case 42: /* return_statement: RETURN expression  */
  if (yyn == 42)
    /* "src/main/bison/parser.y":273  */
    {
        yyval = new ReturnNode(((Node)(yystack.valueAt (0))));
    };
  break;


  case 43: /* expression: postfix_expression  */
  if (yyn == 43)
    /* "src/main/bison/parser.y":280  */
    {
        yyval = ((Node)(yystack.valueAt (0)));
    };
  break;


  case 44: /* postfix_expression: primary  */
  if (yyn == 44)
    /* "src/main/bison/parser.y":287  */
    {
        yyval = ((Node)(yystack.valueAt (0)));
    };
  break;


  case 45: /* postfix_expression: postfix_expression DOT IDENTIFIER  */
  if (yyn == 45)
    /* "src/main/bison/parser.y":291  */
    {
        yyval = new MemberAccessNode(((Node)(yystack.valueAt (2))), new VariableNode(((String)(yystack.valueAt (0)))));
    };
  break;


  case 46: /* postfix_expression: postfix_expression DOT IDENTIFIER BRACKET_L BRACKET_R  */
  if (yyn == 46)
    /* "src/main/bison/parser.y":295  */
    {
        Node member = new MemberAccessNode(((Node)(yystack.valueAt (4))), new VariableNode(((String)(yystack.valueAt (2)))));
        yyval = new FunctionCallNode(member, new ArrayList<Node>());
    };
  break;


  case 47: /* postfix_expression: postfix_expression DOT IDENTIFIER BRACKET_L argument_list BRACKET_R  */
  if (yyn == 47)
    /* "src/main/bison/parser.y":300  */
    {
        Node member = new MemberAccessNode(((Node)(yystack.valueAt (5))), new VariableNode(((String)(yystack.valueAt (3)))));
        yyval = new FunctionCallNode(member, ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 48: /* primary: INT  */
  if (yyn == 48)
    /* "src/main/bison/parser.y":308  */
    {
        yyval = new NumberNode(((Integer)(yystack.valueAt (0))));
    };
  break;


  case 49: /* primary: REAL  */
  if (yyn == 49)
    /* "src/main/bison/parser.y":312  */
    {
        yyval = new RealNumberNode(((Double)(yystack.valueAt (0))));
    };
  break;


  case 50: /* primary: BOOLEAN  */
  if (yyn == 50)
    /* "src/main/bison/parser.y":316  */
    {
        yyval = new BooleanNumberNode(((Boolean)(yystack.valueAt (0))));
    };
  break;


  case 51: /* primary: IDENTIFIER  */
  if (yyn == 51)
    /* "src/main/bison/parser.y":320  */
    {
        yyval = new VariableNode(((String)(yystack.valueAt (0))));
    };
  break;


  case 52: /* primary: THIS  */
  if (yyn == 52)
    /* "src/main/bison/parser.y":324  */
    {
        yyval = new ThisNode();
    };
  break;


  case 53: /* primary: function_call  */
  if (yyn == 53)
    /* "src/main/bison/parser.y":328  */
    {
        yyval = ((Node)(yystack.valueAt (0)));
    };
  break;


  case 54: /* primary: BRACKET_L expression BRACKET_R  */
  if (yyn == 54)
    /* "src/main/bison/parser.y":332  */
    {
        yyval = ((Node)(yystack.valueAt (1)));
    };
  break;


  case 55: /* function_call: IDENTIFIER BRACKET_L BRACKET_R  */
  if (yyn == 55)
    /* "src/main/bison/parser.y":339  */
    {
        yyval = new FunctionCallNode(
            new VariableNode(((String)(yystack.valueAt (2)))),
            new ArrayList<Node>()
        );
    };
  break;


  case 56: /* function_call: IDENTIFIER BRACKET_L argument_list BRACKET_R  */
  if (yyn == 56)
    /* "src/main/bison/parser.y":346  */
    {
        yyval = new FunctionCallNode(new VariableNode(((String)(yystack.valueAt (3)))), ((List<Node>)(yystack.valueAt (1))));
    };
  break;


  case 57: /* argument_list: expression  */
  if (yyn == 57)
    /* "src/main/bison/parser.y":353  */
    {
        List<Node> list = new ArrayList<Node>();
        list.add(((Node)(yystack.valueAt (0))));
        yyval = list;
    };
  break;


  case 58: /* argument_list: argument_list COMMA expression  */
  if (yyn == 58)
    /* "src/main/bison/parser.y":359  */
    {
        ((List<Node>)(yystack.valueAt (2))).add(((Node)(yystack.valueAt (0))));
        yyval = ((List<Node>)(yystack.valueAt (2)));
    };
  break;



/* "src/main/java/com/compiler/Parser.java":1034  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Object yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(Parser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private Parser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = Parser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }





  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param yyctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      yyerror("syntax error");
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final short yypact_ninf_ = -27;
  private static final byte yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
     -27,     4,     8,   -27,    17,   -27,    54,    27,   -27,    26,
      47,   -27,   -27,    42,    58,     5,   -27,   -27,   -27,    -6,
     -27,   175,    15,    35,   -27,    -5,   -27,   178,   -27,   -27,
     178,    12,    67,    64,    50,    70,    68,   -27,    83,   -27,
     -27,   -27,    61,   -27,   178,   -27,    59,   -27,   -27,   -27,
      66,    78,   -27,     0,   -27,   178,   178,   178,   -27,   -27,
     -27,   -27,   -27,   -27,   -27,    89,   -27,    94,    92,   -27,
       3,    95,   105,   108,    96,   178,   113,   112,   -27,   -27,
     102,   -27,   -27,   -27,   -27,    87,   -27,   109,   -27,   127,
     -27,   -27,   -27,   -27,   121,   -27,   178,     7,   -27,   140,
      33,   -27,   -27,   -27,    97,   -27,   -27,   -27,   -27,   159,
     -27
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte[] yydefact_ = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       3,     0,     2,     1,     0,     4,     0,     0,     7,     0,
       0,     7,     5,     0,     0,     0,     8,     9,    10,    13,
      11,     0,     0,    15,    29,     0,    29,     0,    14,     6,
       0,     0,     0,     0,     0,     0,     0,    23,     0,    48,
      49,    50,    51,    52,     0,    22,    43,    44,    53,    12,
      16,     0,    18,    51,    26,     0,     0,    41,    31,    30,
      32,    33,    34,    35,    36,     0,    29,     0,     0,    21,
       0,     0,     0,     0,    17,     0,     0,     0,    42,    25,
       0,    29,    24,    55,    57,     0,    54,    45,    19,     0,
      37,    29,    29,    27,     0,    56,     0,     0,    20,     0,
       0,    28,    58,    46,     0,    38,    39,    29,    47,     0,
      40
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
     -27,   -27,   -27,   -27,   125,   -27,    32,   -27,   -27,   -27,
     107,    71,   -27,   -26,   -27,   -27,   -27,   -27,   -27,   -25,
     -27,   -27,   -27,    44
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final byte[] yydefgoto_ = yydefgoto_init();
  private static final byte[] yydefgoto_init()
  {
    return new byte[]
    {
       0,     1,     2,     5,    10,    16,    58,    18,    19,    28,
      36,    37,    20,    33,    59,    60,    61,    62,    63,    64,
      46,    47,    48,    85
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final byte[] yytable_ = yytable_init();
  private static final byte[] yytable_init()
  {
    return new byte[]
    {
      38,    34,    45,    26,     3,    49,    39,    40,    41,    42,
      39,    40,    41,    42,    24,     4,    43,    35,    34,    71,
      43,    70,    27,     6,    44,    83,    25,    75,    44,   103,
      76,    77,    78,     9,    50,    11,    39,    40,    41,    53,
      80,    30,    17,   106,    13,    84,    43,    55,    22,    56,
      90,   107,    57,    17,    44,    94,    31,    12,    13,    14,
      15,    32,     7,     8,    23,    99,   100,    39,    40,    41,
      53,   102,    84,    52,    54,    13,    65,    43,    55,    66,
      56,   109,    70,    57,    72,    44,    39,    40,    41,    53,
      67,    68,    73,    69,    13,    79,    43,    55,    34,    56,
      74,    68,    57,    81,    44,    39,    40,    41,    53,    95,
      96,    87,    93,    13,    88,    43,    55,    86,    56,   108,
      96,    57,    89,    44,    39,    40,    41,    53,    91,    92,
      97,   101,    13,    98,    43,    55,    21,    56,    51,    82,
      57,   104,    44,    39,    40,    41,    53,     0,     0,     0,
     105,    13,     0,    43,    55,     0,    56,     0,     0,    57,
       0,    44,    39,    40,    41,    53,     0,     0,     0,   110,
      13,     0,    43,    55,     0,    56,     0,     0,    57,     0,
      44,    39,    40,    41,    42,    29,    13,    14,    15,     0,
       0,    43,     0,     0,     0,     0,     0,     0,     0,    44
    };
  }

private static final byte[] yycheck_ = yycheck_init();
  private static final byte[] yycheck_init()
  {
    return new byte[]
    {
      26,     6,    27,     9,     0,    30,     3,     4,     5,     6,
       3,     4,     5,     6,     9,     7,    13,    22,     6,    44,
      13,    21,    28,     6,    21,    22,    21,    27,    21,    22,
      55,    56,    57,     6,    22,     9,     3,     4,     5,     6,
      66,    26,    10,    10,    11,    70,    13,    14,     6,    16,
      75,    18,    19,    21,    21,    81,    21,    10,    11,    12,
      13,    26,     8,     9,     6,    91,    92,     3,     4,     5,
       6,    96,    97,     6,    10,    11,    26,    13,    14,     9,
      16,   107,    21,    19,    25,    21,     3,     4,     5,     6,
      22,    23,    26,    10,    11,     6,    13,    14,     6,    16,
      22,    23,    19,     9,    21,     3,     4,     5,     6,    22,
      23,     6,    10,    11,     6,    13,    14,    22,    16,    22,
      23,    19,    26,    21,     3,     4,     5,     6,    15,    17,
      21,    10,    11,     6,    13,    14,    11,    16,    31,    68,
      19,    97,    21,     3,     4,     5,     6,    -1,    -1,    -1,
      10,    11,    -1,    13,    14,    -1,    16,    -1,    -1,    19,
      -1,    21,     3,     4,     5,     6,    -1,    -1,    -1,    10,
      11,    -1,    13,    14,    -1,    16,    -1,    -1,    19,    -1,
      21,     3,     4,     5,     6,    10,    11,    12,    13,    -1,
      -1,    13,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    21
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,    30,    31,     0,     7,    32,     6,     8,     9,     6,
      33,     9,    10,    11,    12,    13,    34,    35,    36,    37,
      41,    33,     6,     6,     9,    21,     9,    28,    38,    10,
      26,    21,    26,    42,     6,    22,    39,    40,    42,     3,
       4,     5,     6,    13,    21,    48,    49,    50,    51,    48,
      22,    39,     6,     6,    10,    14,    16,    19,    35,    43,
      44,    45,    46,    47,    48,    26,     9,    22,    23,    10,
      21,    48,    25,    26,    22,    27,    48,    48,    48,     6,
      42,     9,    40,    22,    48,    52,    22,     6,     6,    26,
      48,    15,    17,    10,    42,    22,    23,    21,     6,    42,
      42,    10,    48,    22,    52,    10,    10,    18,    22,    42,
      10
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    29,    30,    31,    31,    32,    32,    33,    33,    34,
      34,    34,    35,    36,    36,    37,    37,    37,    37,    37,
      37,    38,    38,    39,    39,    40,    41,    41,    41,    42,
      42,    42,    43,    43,    43,    43,    43,    44,    45,    46,
      46,    47,    47,    48,    49,    49,    49,    49,    50,    50,
      50,    50,    50,    50,    50,    51,    51,    52,    52
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     1,     0,     2,     5,     7,     0,     2,     1,
       1,     1,     4,     1,     2,     2,     4,     5,     4,     6,
       7,     3,     2,     1,     3,     3,     4,     6,     7,     0,
       2,     2,     1,     1,     1,     1,     1,     3,     5,     5,
       7,     1,     2,     1,     1,     3,     5,     6,     1,     1,
       1,     1,     1,     1,     3,     3,     4,     1,     3
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 257;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     3,     4,     5,     6,     7,     8,     9,    10,    11,
      12,    13,    14,    15,    16,    17,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2
    };
  }


  private static final int YYLAST_ = 199;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 3;
  private static final int YYNTOKENS_ = 29;

/* Unqualified %code blocks.  */
/* "src/main/bison/parser.y":14  */

    private Node rootNode;
    public Node getRootNode() {
        return rootNode;
    }

/* "src/main/java/com/compiler/Parser.java":1634  */

}
/* "src/main/bison/parser.y":365  */
