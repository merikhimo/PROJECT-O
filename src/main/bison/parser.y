%language "Java"
//точка входа
%package "com.compiler"
%define parser_class_name "Parser"

%code imports {
    //тут все ноды надо будет прописать
    import com.compiler.ast.*; 
}

%token <Integer> INT
%token <Double>  REAL
%token <Float>   FLOAT
%token <Boolean> BOOLEAN
%token <Object>  ANY_VALUE

%token PLUS MULT DIV MINUS

%token BRACKET_L BRACKET_R SQR_BRACKET_L SQR_BRACKET_R COMMA SEMICOLON DOT

// Все правила грамматики возвращают базовый тип Node
%type <Node> expression

%%

program:
    expression {
        this.rootNode = $1; 
        System.out.println("AST build was successful");
    }
    ;


// PlusNode NumberNode... надо будет прописать в ./ast 
// Пока что простые, без внутренней логики, этим уже на этапе семантики займемся
// Тут я все сделаю до вторника, закиньте пожалуйста все тесты в ./test
// Саша

expression:
    // у нас не инфиксная нотация, поэтому пока что заглушка
    // будет что то вида ANY VALUE DOT operation LBRACKET ANY_VALUE RBRACKET 
    expression PLUS expression {
        $$ = new PlusNode($1, $3); 
    }
    | expression MINUS expression {
        $$ = new MinusNode($1, $3);
    }
    | expression MULT expression {
        $$ = new MultiplicationNode($1, $3);
    }
    | expression DIV expression {
        $$ = new DivisionNode($1, $3);
    }
    | INT {
        $$ = new NumberNode($1);
    }
    | REAL {
        $$ = new RealNumberNode($1);
    }
    | FLOAT {
        $$ = new FloatNumberNode($1);
    }
    | BOOLEAN {
        $$ = new BooleanNumberNode($1);
    }
    | ANY_VALUE{
        $$ = new AnyValueNode($1);
    }
    | 
    ;
%%
