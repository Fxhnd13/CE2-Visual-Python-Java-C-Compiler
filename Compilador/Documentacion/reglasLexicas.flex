MACROS

	/* special chars */
	LineTerminator = \r|\n|\r\n
	InputCharacter = [^\r\n]
	WhiteSpace = [ \t\f]+

	/* identifiers */
	L = [a-zA-Z]

	/* integer literals */
	Digito = [0-9]
	IntegerLiteral = 0 | [1-9]{Digito}*

	/* comments */
	TraditionalComment   = "/*" [^*] ~"*/"
	EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
	Comment = {TraditionalComment} | {EndOfLineComment}

EXPRESIONES_REGULARES

    /* Reserved words */
    "%%"{WhiteSpace}*"JAVA"                                                     { yybegin(JAVA); return symbol(yyline+1, yycolumn+1, "%%JAVA", sym.SEPARADOR_JAVA); }
    "%%"{WhiteSpace}*"VB"                                                       { return symbol(yyline+1, yycolumn+1, "%%VB", sym.SEPARADOR_VB);}
    ("P"|"p")("U"|"u")("B"|"b")("L"|"l")("I"|"i")("C"|"c")                       { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PUBLIC); }
    ("e"|"E")("n"|"N")("d"|"D")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_END);}
    ("F"|"f")("U"|"u")("N"|"n")("C"|"c")("T"|"t")("I"|"i")("O"|"o")("N"|"n")    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FUNCTION); }
    ("A"|"a")("S"|"s")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_AS);}
    ("S"|"s")("U"|"u")("B"|"b")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_SUB); }
    ("R"|"r")("E"|"e")("T"|"t")("U"|"u")("R"|"r")("N"|"n")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RETURN); }
    ("F"|"f")("O"|"o")("R"|"r")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FOR); }
    ("T"|"t")("O"|"o")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_TO); }
    ("S"|"s")("T"|"t")("E"|"e")("P"|"p")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_STEP); }
    ("W"|"w")("H"|"h")("I"|"i")("L"|"l")("E"|"e")                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_WHILE); }
    ("D"|"d")("O"|"o")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DO); }
    ("L"|"l")("O"|"o")("O"|"o")("P"|"p")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_LOOP); }
    ("I"|"i")("F"|"f")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    ("E"|"e")("L"|"l")("S"|"s")("E"|"e")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSE); }
    ("E"|"e")("L"|"l")("S"|"s")("E"|"e")("I"|"i")("F"|"f")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSEIF); }
    ("T"|"t")("H"|"h")("E"|"e")("N"|"n")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_THEN); }
    ("S"|"s")("E"|"e")("L"|"l")("E"|"e")("C"|"c")("T"|"t")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_SELECT); }
    ("C"|"c")("A"|"a")("S"|"s")("E"|"e")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CASE); }
    ("D"|"d")("I"|"i")("M"|"m")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DIM); }
    ("N"|"n")("E"|"e")("X"|"x")("T"|"t")                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_NEXT); }

    // mensajes a pantalla
    ("C"|"c")("O"|"o")("N"|"n")("S"|"s")("O"|"o")("L"|"l")("E"|"e")("\.")("W"|"w")("R"|"r")("I"|"i")("T"|"t")("E"|"e")                      {return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJE);}
    ("C"|"c")("O"|"o")("N"|"n")("S"|"s")("O"|"o")("L"|"l")("E"|"e")("\.")("W"|"w")("R"|"r")("I"|"i")("T"|"t")("E"|"e")("L"|"l")("N"|"n")    {return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJELN);}
    ("C"|"c")("O"|"o")("N"|"n")("S"|"s")("O"|"o")("L"|"l")("E"|"e")("\.")("W"|"w")("R"|"r")("I"|"i")("T"|"t")("E"|"e")("L"|"l")("I"|"i")("N"|"n")("E"|"e")    {return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJELN);}

    /* tipos de datos y su ingreso */
    ("I"|"i")("N"|"n")("T"|"t")("E"|"e")("G"|"g")("E"|"e")("R"|"r")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ENTERO); }
    ("D"|"d")("E"|"e")("C"|"c")("I"|"i")("M"|"m")("A"|"a")("L"|"l")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FLOTANTE); }
    ("C"|"c")("H"|"h")("A"|"a")("R"|"r")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CARACTER); }
    "intinput"                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.INTINPUT); }
    "floatinput"                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOATINPUT); }
    "charinput"                           { return symbol(yyline+1, yycolumn+1, yytext(), sym.CHARINPUT); }

    // operadores y agrupadores
    "("                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    "*"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "+"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "-"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "/"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIVISION); }
    ("M"|"m")("O"|"o")("D"|"d")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //otros simbolos
    "="                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    ","                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }
    "&"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.AMPERSON); }
    {LineTerminator}                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.FIN_LINEA); }

    //operadores logicos y comparativos
    "<>"                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    ">"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ("<="|"=<")                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR_IGUAL); }
    (">="|"=>")                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR_IGUAL); }
    ("N"|"n")("O"|"o")("T"|"t")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }
    ("A"|"a")("N"|"n")("D"|"d")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    ("O"|"o")("R"|"r")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    

    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.CARACTER);}
    "\""                                            { yybegin(CADENA_VB); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }

    /* error fallback */
    [^]                                     { errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}
}

<JAVA>{
    //palabras reservadas
    "%%"{WhiteSpace}*"PY"                             { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, "%%PY", sym.SEPARADOR_PY); }
    "while"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_WHILE); }
    "do"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DO); }
    "for"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FOR); }
    "break"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_BREAK); }
    "switch"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_SWITCH); }
    "case"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CASE); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "else"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSE); }
    "default"                                           { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DEFAULT); }
    "public"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PUBLIC); }
    "void"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_VOID); }
    "class"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CLASS); }
    "return"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RETURN); }
    "this."                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_THIS); }

    //mensajes a pantalla
    "System.out.print"                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJE); }
    "System.out.println"                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJELN); }

    // tipos de datos y su ingreso
    "int"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ENTERO); }
    "float"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FLOTANTE); }
    "char"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CARACTER); }
    "intinput"                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.INTINPUT); }
    "floatinput"                                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOATINPUT); }
    "charinput"                                       { return symbol(yyline+1, yycolumn+1, yytext(), sym.CHARINPUT); }

    //operadores
    "+"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "++"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.AUMENTO); }
    "-"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "--"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISMINUCION); }
    "*"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "/"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIVISION); }
    "%"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }
    "="                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }

    //operadores de comparacion y logicos
    "!"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }
    "<"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR_IGUAL); }
    ">="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR_IGUAL); }
    "!="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "||"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "&&"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "="                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }

    //simbolos extras
    "("                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    "{"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.LLAVE_A); }
    "}"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.LLAVE_C); }
    ";"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.FIN_LINEA); }
    ","                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }
    ":"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DOS_PUNTOS); }
    
    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext().substring(1,2), sym.CARACTER);}
    "\""                                            { yybegin(CADENA_JV); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }
    {LineTerminator}                        { /* Ignora los saltos de linea */}

    /* error fallback */
    [^]                                     { errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}
}

<PYTHON>{
    //palabras reservadas
    "%%"{WhiteSpace}*"PROGRAMA"                         { yybegin(PRINCIPAL); return symbol(yyline+1, yycolumn+1, "%%PROGRAMA", sym.SEPARADOR_PROGRAMA); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "for"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FOR); }
    "in"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IN); }
    "range"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RANGE); }
    "while"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_WHILE); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "elif"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELIF); }
    "else"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSE); }
    "pass"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PASS); }
    "def"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DEF); }
    "return"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RETURN); }

    //mensajes a pantalla
    "print"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJE); }
    "println"                                           { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJELN); }

    //Ingreso de los tipos de datos
    "intinput"                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.INTINPUT); }
    "floatinput"                                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOATINPUT); }
    "charinput"                                       { return symbol(yyline+1, yycolumn+1, yytext(), sym.CHARINPUT); }

    //operadores
    "+"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "*"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "-"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "/"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIVISION); }
    "%"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //operadores logicos y comparativos
    "="                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }
    "!="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "<"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR_IGUAL); }
    ">="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR_IGUAL); }
    "and"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "or"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "not"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }

    //simbolos extras
    "("                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    ":"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DOS_PUNTOS); }
    ","                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }

    {WhiteSpace}                                        { /* se ignoran los espacios en blanco*/ }
    {LineTerminator}                                    { yybegin(PYTHON2); return symbol(yyline+1, yycolumn+1, yytext(), sym.FIN_LINEA); }
    
    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext().substring(1,2), sym.CARACTER);}
    "\""                                            { yybegin(CADENA_PY); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}

    /* error fallback */
    [^]                                     { errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}
}

<PYTHON2>{
    //palabras reservadas
    "%%"{WhiteSpace}*"PROGRAMA"                         { yybegin(PRINCIPAL); return symbol(yyline+1, yycolumn+1, "%%PROGRAMA", sym.SEPARADOR_PROGRAMA); }
    "if"                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "for"                                               { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FOR); }
    "in"                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IN); }
    "range"                                             { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RANGE); }
    "while"                                             { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_WHILE); }
    "if"                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "elif"                                              { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELIF); }
    "else"                                              { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSE); }
    "pass"                                              { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PASS); }
    "def"                                               { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DEF); }
    "return"                                            { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_RETURN); }

    //mensajes a pantalla
    "print"                                             { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJE); }
    "println"                                           { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MENSAJELN); }

    //Ingreso de los tipos de datos
    "intinput"                                          { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.INTINPUT); }
    "floatinput"                                        { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOATINPUT); }
    "charinput"                                         { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.CHARINPUT); }

    //operadores
    "+"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "*"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "-"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "/"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.DIVISION); }
    "%"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //operadores logicos y comparativos
    "="                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }
    "!="                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "<"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR_IGUAL); }
    ">="                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR_IGUAL); }
    "and"                                               { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "or"                                                { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "not"                                               { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }

    //simbolos extras
    "("                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    ":"                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.DOS_PUNTOS); }
    ","                                                 { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }

    " "                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.ESPACIO); }
    "\t"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.TABULACION); }

    {LineTerminator}                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.FIN_LINEA); }
    
    /* identificadores */
    {L}({L}|{Digito}|("\_"))*                           { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                    { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])              { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                       { yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, yytext().substring(1,2), sym.CARACTER);}
    "\""                                                { yybegin(CADENA_PY); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}

    /* error fallback */
    [^]                                     { yybegin(PYTHON); errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}
}

<PRINCIPAL>{
    //palabras reservadas
    "void"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_VOID); }
    "public"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PUBLIC); }
    "main"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_MAIN); }
    "#"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.HASHTAG); }
    "include"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_INCLUDE); }
    "const"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CONST); }
    "if"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_IF); }
    "else"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ELSE); }
    "switch"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_SWITCH); }
    "case"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CASE); }
    "break"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_BREAK); }
    "for"                                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FOR); }
    "while"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_WHILE); }
    "do"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DO); }
    "VB"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_VB); }
    "PY"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PY); }
    "JAVA"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_JAVA); }
    "clrscr"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_LIMPIAR_PANTALLA); }
    "getch"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ESPERAR_TECLA); }
    "default"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_DEFAULT); }

    //operadores
    "+"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "-"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "*"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "/"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIVISION); }
    "%"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //operadores logicos y comparativos
    "<"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR_IGUAL); }
    ">="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR_IGUAL); }
    "="                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }
    "!="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "&"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.AMPERSON); }
    "&&"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "||"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "!"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }

    //mensajes a pantalla
    "printf"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PRINTF); }

    //declaracion e ingreso de tipos de datos
    "int"                                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ENTERO); }
    "char"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CARACTER); }
    "float"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FLOTANTE); }
    "const"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CONST); }
    "scanf"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_SCANF); }

    //simbolos extras
    "("                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    "["                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.COR_A); }
    "]"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.COR_C); }
    "{"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.LLAVE_A); }
    "}"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.LLAVE_C); }
    ";"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.FIN_LINEA); }
    ":"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.DOS_PUNTOS); }
    ","                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }
    "\."                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PUNTO); }

    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext().substring(1,2), sym.CARACTER);}
    "\""                                            { cadena.setLength(0); yybegin(CADENA_PRINCIPAL); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }
    {LineTerminator}                        {/* ignora los saltos de linea */}

    /* error fallback */
    [^]                                     { errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}

}

<CADENA_PRINCIPAL> {
    "P"                                     { cadena.append("P"); }
    "J"                                     { cadena.append("J"); }
    "V"                                     { cadena.append("V"); }
    "PY"                                    {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_PY); 
                                                }
                                            }
    "JAVA."                                  {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_JAVA); 
                                                }
                                            }
    "VB"                                    {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.PR_VB);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_VB); 
                                                }
                                            }
    "\%d"                                   {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.COMODIN_ENTERO); 
                                                }
                                            }
    "\%c"                                   {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.COMODIN_CARACTER); 
                                                }
                                            }
    "\%f"                                   {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.COMODIN_FLOTANTE); 
                                                } 
                                            }
    "\""                                    {   yybegin(PRINCIPAL);  
                                                if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, (lexema.length() > 1)? sym.CADENA : sym.CARACTER);
                                                }
                                            }
    [^"\"""\%""P""J""V"]                    { cadena.append(yytext()); }
}

<CADENA_PY>{
    "\""                                    { yybegin(PYTHON); }
    [^"\""]+                                { return symbol(yyline+1, yycolumn+1, yytext(), (yytext().length() > 1)? sym.CADENA : sym.CARACTER); }
}

<CADENA_JV>{
    "\""                                    { yybegin(JAVA); }
    [^"\""]+                                { return symbol(yyline+1, yycolumn+1, yytext(), (yytext().length() > 1)? sym.CADENA : sym.CARACTER); }
}

<CADENA_VB>{
    "\""                                    { yybegin(YYINITIAL); }
    [^"\""]+                                { return symbol(yyline+1, yycolumn+1, yytext(), (yytext().length() > 1)? sym.CADENA : sym.CARACTER); }
}

    [^]                                     { errores.add(new ErrorAnalisis("Lexico",yytext(),"Caracter no aceptado",yyline+1, yycolumn+1));}
