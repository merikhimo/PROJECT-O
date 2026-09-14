%language "Java"
//точка входа
%package "com.compiler"
%define parser_class_name "Parser"

%code imports {
    //тут все ноды надо будет прописать
    import com.compiler.ast.*;
    import java.util.List;
    import java.util.ArrayList;
}

//I think i should write comments in english

//nodes to implement:
// Node
// ProgramNode
// ClassDeclNode
// VariableDeclNode
// MethodDeclNode + Body + Header 
// ParameterNode
// ConstructNode
// AssignmentNode
// WhileNode
// IfNode
// ReturnNode
// MemberAccessNode
// ConstructorInvocNode

%code {
    private Node rootNode;
    public Node getRootNode() {
        return rootNode;
    }
}

%token <Integer> INT 1
%token <Double>  REAL 2
%token <Boolean> BOOLEAN 3
%token <String>  INDENTIFIER 4
%token CLASS 5
%token EXTENDS 6
%token IS 7
%token END 8
%token VAR 9
%token METHOD 10
%token THIS 11
%token WHILE 12
%token LOOP 13
%token IF 14
%token THEN 15
%token ELSE 16
%token RETURN 17
%token NULL 18
%token BRACKET_L 19
%token BRACKET_R 20
%token COMMA 21
%token SEMICOLON 22
%token DOT 23
%token COLON 24
%token ASSIGN 25
%token ARROW 26

%type <Node> program
%type <Node> class_declaration
%type <Node> member
%type <Node> variable_declaration
%type <Node> method_declaration
%type <Node> method_header
%type <Node> method_body
%type <Node> constructor_declaration
%type <Node> parameter
%type <Node> statement
%type <Node> assignment
%type <Node> while_loop
%type <Node> if_statement
%type <Node> return_statement
%type <Node> expression
%type <Node> primary
%type <Node> constructor_invocation
%type <Node> function_call

%type <List<Node>> class_declaration_list
%type <List<Node>> member_list
%type <List<Node>> parameter_list
%type <List<Node>> statement_list
%type <List<Node>> argument_list

%%

program:
      class_declaration_list
      {
          rootNode = new ProgramNode($1);
          $$ = rootNode;
      }
    ;

class_declaration_list:
      {
          $$ = new ArrayList<Node>();
      }
    | class_declaration_list class_declaration
      {
          $1.add($2);
          $$ = $1;
      }
    ;

class_declaration:
      CLASS IDENTIFIER IS member_list END
      {
          $$ = new ClassDeclNode(
              $2,
              null,
              $4
          );
      }
    | CLASS IDENTIFIER EXTENDS IDENTIFIER IS member_list END
      {
          $$ = new ClassDeclNode(
              $2,
              $4,
              $6
          );
      }
    ;

member_list:
      {
          $$ = new ArrayList<Node>();
      }
    | member_list member
      {
          $1.add($2);
          $$ = $1;
      }
    ;

member:
      variable_declaration
      {
          $$ = $1;
      }
    | method_declaration
      {
          $$ = $1;
      }
    | constructor_declaration
      {
          $$ = $1;
      }
    ;

variable_declaration:
      VAR IDENTIFIER COLON expression
      {
          $$ = new VariableDeclNode($2, $4);
      }
    ;


method_declaration:
      method_header
      {
          $$ = new MethodDeclNode($1, null);
      }
    | method_header method_body
      {
          $$ = new MethodDeclNode($1, $2);
      }
    ;

method_header:
      METHOD IDENTIFIER
      {
          $$ = new MethodHeaderNode(
              $2,
              new ArrayList<Node>(),
              null
          );
      }
    | METHOD IDENTIFIER BRACKET_L parameter_list BRACKET_R
      {
          $$ = new MethodHeaderNode(
              $2,
              $4,
              null
          );
      }
    | METHOD IDENTIFIER COLON IDENTIFIER
      {
          $$ = new MethodHeaderNode(
              $2,
              new ArrayList<Node>(),
              $4
          );
      }
    | METHOD IDENTIFIER BRACKET_L parameter_list BRACKET_R
      COLON IDENTIFIER
      {
          $$ = new MethodHeaderNode(
              $2,
              $4,
              $7
          );
      }
    ;

method_body:
      IS statement_list END
      {
          $$ = new MethodBodyNode($2);
      }
    | ARROW expression
      {
          $$ = new ExpressionBodyNode($2);
      }
    ;

parameter_list:
      parameter
      {
          List<Node> list = new ArrayList<Node>();
          list.add($1);
          $$ = list;
      }
    | parameter_list COMMA parameter
      {
          $1.add($3);
          $$ = $1;
      }
    ;

parameter:
      IDENTIFIER COLON IDENTIFIER
      {
          $$ = new ParameterNode($1, $3);
      }
    ;

constructor_declaration:
      THIS IS statement_list END
      {
          $$ = new ConstructNode(
              new ArrayList<Node>(),
              $3
          );
      }
    | THIS BRACKET_L parameter_list BRACKET_R IS statement_list END
      {
          $$ = new ConstructNode(
              $3,
              $6
          );
      }
    ;

statement_list:
      {
          $$ = new ArrayList<Node>();
      }
    | statement_list statement
      {
          $1.add($2);
          $$ = $1;
      }
    | statement_list variable_declaration
      {
          $1.add($2);
          $$ = $1;
      }
    ;


statement:
      assignment
      {
          $$ = $1;
      }
    | while_loop
      {
          $$ = $1;
      }
    | if_statement
      {
          $$ = $1;
      }
    | return_statement
      {
          $$ = $1;
      }
    ;

assignment:
      IDENTIFIER ASSIGN expression
      {
          $$ = new AssignmentNode($1, $3);
      }
    ;

while_loop:
      WHILE expression LOOP statement_list END
      {
          $$ = new WhileNode($2, $4);
      }
    ;

if_statement:
      IF expression THEN statement_list END
      {
          $$ = new IfNode(
              $2,
              $4,
              new ArrayList<Node>()
          );
      }
    | IF expression THEN statement_list ELSE statement_list END
      {
          $$ = new IfNode(
              $2,
              $4,
              $6
          );
      }
    ;

return_statement:
      RETURN
      {
          $$ = new ReturnNode(null);
      }
    | RETURN expression
      {
          $$ = new ReturnNode($2);
      }
    ;

expression:
      primary
      {
          $$ = $1;
      }
    | constructor_invocation
      {
          $$ = $1;
      }
    | function_call
      {
          $$ = $1;
      }
    | expression DOT expression
      {
          $$ = new MemberAccessNode($1, $3);
      }
    ;

constructor_invocation:
      IDENTIFIER
      {
          $$ = new ConstructorInvocNode(
              $1,
              new ArrayList<Node>()
          );
      }
    | IDENTIFIER BRACKET_L BRACKET_R
      {
          $$ = new ConstructorInvocNode(
              $1,
              new ArrayList<Node>()
          );
      }
    | IDENTIFIER BRACKET_L argument_list BRACKET_R
      {
          $$ = new ConstructorInvocNode(
              $1,
              $3
          );
      }
    ;


function_call:
      expression BRACKET_L BRACKET_R
      {
          $$ = new FunctionCallNode(
              $1,
              new ArrayList<Node>()
          );
      }
    | expression BRACKET_L argument_list BRACKET_R
      {
          $$ = new FunctionCallNode(
              $1,
              $3
          );
      }
    ;

argument_list:
      expression
      {
          List<Node> list = new ArrayList<Node>();
          list.add($1);
          $$ = list;
      }
    | argument_list COMMA expression
      {
          $1.add($3);
          $$ = $1;
      }
    ;

primary:
      INT
      {
          $$ = new NumberNode($1);
      }
    | REAL
      {
          $$ = new RealNumberNode($1);
      }
    | BOOLEAN
      {
          $$ = new BooleanNumberNode($1);
      }
    | THIS
      {
          $$ = new ThisNode();
      }
    ;

%%