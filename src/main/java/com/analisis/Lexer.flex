package com.analisis;

import com.analisis.objetos.analisis.ErrorManager;
import com.analisis.objetos.analisis.Pos;
import java_cup.runtime.*;
import java.util.ArrayList;

%%

%class Lexer
%public
%cup
%line
%column
%states JAVA, PYTHON, PRINCIPAL, STRING

/* special chars */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]+
espacio = [\r\f ]+
tab = "    "|"\t"

/* identifiers */
L = [a-zA-Z]

/* integer literals */
Digito = [0-9]
Entero = [1-9]
IntegerLiteral = 0 | {Entero}{Digito}*

/* comments */
TraditionalComment   = "/*" [^*] ~"*/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
Comment = {TraditionalComment} | {EndOfLineComment}

%{
    private StringBuffer cadena = new StringBuffer();
    private ArrayList<Integer> integers = new ArrayList<>();
    private ErrorManager errores = new ErrorManager();
    private boolean analizando;
    private boolean verificarident = false;
    private int seccionActual=0;

    private Symbol symbol(int linea, int columna, String lexema, int type){
        Symbol simbolo = new Symbol(type, linea, columna, lexema);
        escribirSimbolo(simbolo);
        return simbolo;
    }

    private Symbol symbol(int linea, int columna, int type){
        Symbol simbolo = new Symbol(type,linea,columna);
        escribirSimbolo(simbolo);
        return simbolo;
    }

    private Symbol symbol(String value, int tam) {
        if(verificarident){
            int i = integers.get(integers.size()-1);
            if(i>(tam-1-value.lastIndexOf('\n'))){
                integers.remove(integers.size()-1);
                this.yypushback(tam - value.lastIndexOf('\n'));
                Symbol simbolo = new Symbol(sym.DEDENT, yyline+1, yycolumn+1, value);
                escribirSimbolo(simbolo);
                return simbolo;
            } else if(i< (tam-1-value.lastIndexOf('\n'))) {
                integers.add(tam-1-value.lastIndexOf('\n'));
                verificarident = false;
                Symbol simbolo = new Symbol(sym.IDENT, yyline+1, yycolumn+1, value);
                escribirSimbolo(simbolo);
                return simbolo;
            } else {
                verificarident = false;
                return null;
            }
        } else {
            this.yypushback(tam - value.lastIndexOf('\n'));
            verificarident = true;
            Symbol simbolo = new Symbol(sym.NEWLINE, yyline+1, yycolumn+1, value);
            escribirSimbolo(simbolo);
            return simbolo;
        }
    }

    public boolean isAnalizando(){ return analizando; }

    private void escribirSimbolo(Symbol simbolo){
        System.out.println("Simbolo{\n    cadena:"+simbolo.value+"\n    tipo:"+sym.terminalNames[simbolo.sym]+"\n    linea:"+simbolo.left+"\n    columna:"+simbolo.right+"\n}\n");
    }

    private void cambiarEstado(){
        switch(seccionActual){
            case 0: yybegin(YYINITIAL); break;
            case 1: yybegin(JAVA); break;
            case 2: yybegin(PYTHON); break;
            case 3: yybegin(PRINCIPAL); break;
        }
    }

%}

%init{
    seccionActual = 0;
    integers.add(0);
    analizando = true;
    yybegin(YYINITIAL);
%init}

%eof{
    analizando = false;
%eof}

%%

/* reglas lexicas */
<YYINITIAL> {

    /* Reserved words */
    "%%"{WhiteSpace}*"JAVA"                                                     { seccionActual=1; yybegin(JAVA); return symbol(yyline+1, yycolumn+1, "%%JAVA", sym.SEPARADOR_JAVA); }
    "%%"{WhiteSpace}*"VB"                                                       { return symbol(yyline+1, yycolumn+1, "%%VB", sym.SEPARADOR_VB);}
    ("P"|"p")("U"|"u")("B"|"b")("L"|"l")("I"|"i")("C"|"c")                       { return symbol(yyline+1, yycolumn+1, yytext(), sym.PUBLIC); }
    ("e"|"E")("n"|"N")("d"|"D")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.END);}
    ("F"|"f")("U"|"u")("N"|"n")("C"|"c")("T"|"t")("I"|"i")("O"|"o")("N"|"n")    { return symbol(yyline+1, yycolumn+1, yytext(), sym.FUNCTION); }
    ("A"|"a")("S"|"s")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.AS);}
    ("S"|"s")("U"|"u")("B"|"b")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.SUB); }
    ("R"|"r")("E"|"e")("T"|"t")("U"|"u")("R"|"r")("N"|"n")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.RETURN); }
    ("F"|"f")("O"|"o")("R"|"r")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.FOR); }
    ("T"|"t")("O"|"o")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.TO); }
    ("S"|"s")("T"|"t")("E"|"e")("P"|"p")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.STEP); }
    ("W"|"w")("H"|"h")("I"|"i")("L"|"l")("E"|"e")                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.WHILE); }
    ("D"|"d")("O"|"o")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.DO); }
    ("L"|"l")("O"|"o")("O"|"o")("P"|"p")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.LOOP); }
    ("I"|"i")("F"|"f")                                                          { return symbol(yyline+1, yycolumn+1, yytext(), sym.IF); }
    ("E"|"e")("L"|"l")("S"|"s")("E"|"e")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELSE); }
    ("E"|"e")("L"|"l")("S"|"s")("E"|"e")("I"|"i")("F"|"f")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELSEIF); }
    ("T"|"t")("H"|"h")("E"|"e")("N"|"n")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.THEN); }
    ("S"|"s")("E"|"e")("L"|"l")("E"|"e")("C"|"c")("T"|"t")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.SELECT); }
    ("C"|"c")("A"|"a")("S"|"s")("E"|"e")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.CASE); }
    ("D"|"d")("I"|"i")("M"|"m")                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIM); }
    ("N"|"n")("E"|"e")("X"|"x")("T"|"t")                                        { return symbol(yyline+1, yycolumn+1, yytext(), sym.NEXT); }
    ("B"|"b")("Y"|"y")("V"|"v")("A"|"a")("L"|"l")                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.BYVAL); }

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
    "/"                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIV); }
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
    ("<="|"=<")                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENORIGUAL); }
    (">="|"=>")                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYORIGUAL); }
    ("N"|"n")("O"|"o")("T"|"t")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }
    ("A"|"a")("N"|"n")("D"|"d")             { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    ("O"|"o")("R"|"r")                      { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    

    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.CARACTER);}
    "\""                                            { yybegin(STRING); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }

    /* error fallback */
    [^]                                     { errores.agregarError("Lexico",yytext(),"Caracter no aceptado",new Pos(yyline+1, yycolumn+1)); }
}

<JAVA>{
    //palabras reservadas
    "%%"{WhiteSpace}*"PY"                             { seccionActual=2; yybegin(PYTHON); return symbol(yyline+1, yycolumn+1, "%%PY", sym.SEPARADOR_PY); }
    "while"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.WHILE); }
    "do"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.DO); }
    "for"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.FOR); }
    "break"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.BREAK); }
    "switch"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.SWITCH); }
    "case"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.CASE); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.IF); }
    "else"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELSE); }
    "default"                                           { return symbol(yyline+1, yycolumn+1, yytext(), sym.DEFAULT); }
    "public"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.PUBLIC); }
    "void"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.VOID); }
    "class"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.CLASS); }
    "return"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.RETURN); }
    "this."                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.THIS); }

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
    "/"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIV); }
    "%"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }
    "="                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }

    //operadores de comparacion y logicos
    "!"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }
    "<"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENORIGUAL); }
    ">="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYORIGUAL); }
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
    "\""                                            { yybegin(STRING); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }
    {LineTerminator}                        { /* Ignora los saltos de linea */}

    /* error fallback */
    [^]                                     { errores.agregarError("Lexico",yytext(),"Caracter no aceptado",new Pos(yyline+1, yycolumn+1)); }
}

<PYTHON>{
    //Identacion
    ("\n"|{espacio}|"\t")*("\n"){tab}*                  {
                                                            Symbol sim = symbol(yytext(), yytext().length()); 
                                                            if(sim!=null){
                                                                return sim;
                                                            }
                                                        }

    //palabras reservadas
    "%%"{WhiteSpace}*"PROGRAMA"                         { seccionActual=3; yybegin(PRINCIPAL); return symbol(yyline+1, yycolumn+1, "%%PROGRAMA", sym.SEPARADOR_PROGRAMA); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.IF); }
    "for"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.FOR); }
    "in"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.IN); }
    "range"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.RANGE); }
    "while"                                             { return symbol(yyline+1, yycolumn+1, yytext(), sym.WHILE); }
    "if"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.IF); }
    "elif"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELIF); }
    "else"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELSE); }
    "pass"                                              { return symbol(yyline+1, yycolumn+1, yytext(), sym.PASS); }
    "def"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.DEF); }
    "return"                                            { return symbol(yyline+1, yycolumn+1, yytext(), sym.RETURN); }

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
    "/"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIV); }
    "%"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //operadores logicos y comparativos
    "="                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }
    "!="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "<"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENORIGUAL); }
    ">="                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYORIGUAL); }
    "and"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "or"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "not"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }

    //simbolos extras
    "("                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_A); }
    ")"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PAR_C); }
    ":"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DOS_PUNTOS); }
    ","                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMA); }
    
    /* identificadores */
    {L}({L}|{Digito}|("\_"))*               { return symbol(yyline+1, yycolumn+1, yytext(), sym.ID);}

    /* valores de datos enteros, flotantes y caracteres */
    {IntegerLiteral}                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.ENTERO);}
    {IntegerLiteral}("\.")({Digito}*[1-9])          { return symbol(yyline+1, yycolumn+1, yytext(), sym.FLOTANTE);}
    ("'")[^]("'")                                   { return symbol(yyline+1, yycolumn+1, yytext().substring(1,2), sym.CARACTER);}
    "\""                                            { yybegin(STRING); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}

    /* error fallback */
    [^]                                     { errores.agregarError("Lexico",yytext(),"Caracter no aceptado",new Pos(yyline+1, yycolumn+1)); }
}

<PRINCIPAL>{
    //palabras reservadas
    "void"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.VOID); }
    "public"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PUBLIC); }
    "main"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAIN); }
    "#"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.HASHTAG); }
    "include"                                               { return symbol(yyline+1, yycolumn+1, yytext(), sym.INCLUDE); }
    "const"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.CONST); }
    "if"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.IF); }
    "else"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.ELSE); }
    "switch"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.SWITCH); }
    "case"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.CASE); }
    "break"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.BREAK); }
    "for"                                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.FOR); }
    "while"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.WHILE); }
    "do"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.DO); }
    "VB"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.VB); }
    "PY"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.PY); }
    "JAVA"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.JAVA); }
    "clrscr"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.LIMPIAR_PANTALLA); }
    "getch"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.ESPERAR_TECLA); }
    "default"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.DEFAULT); }

    //operadores
    "+"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAS); }
    "-"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOS); }
    "*"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.POR); }
    "/"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.DIV); }
    "%"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MOD); }

    //operadores logicos y comparativos
    "<"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENOR); }
    ">"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYOR); }
    "<="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.MENORIGUAL); }
    ">="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.MAYORIGUAL); }
    "="                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.IGUAL); }
    "=="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.COMPARADOR); }
    "!="                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.DISTINTO); }
    "&"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.AMPERSON); }
    "&&"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.AND); }
    "||"                                                    { return symbol(yyline+1, yycolumn+1, yytext(), sym.OR); }
    "!"                                                     { return symbol(yyline+1, yycolumn+1, yytext(), sym.NOT); }

    //mensajes a pantalla
    "printf"                                                { return symbol(yyline+1, yycolumn+1, yytext(), sym.PRINTF); }

    //declaracion e ingreso de tipos de datos
    "int"                                                   { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_ENTERO); }
    "char"                                                  { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_CARACTER); }
    "float"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.PR_FLOTANTE); }
    "const"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.CONST); }
    "scanf"                                                 { return symbol(yyline+1, yycolumn+1, yytext(), sym.SCANF); }

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
    "\""                                            { cadena.setLength(0); yybegin(STRING); }

    /* cosas que ignorar */
    {Comment}                               { /* se ignoran los comentarios */}
    {WhiteSpace}                            {   /* Ignora los espacios en blanco */  }
    {LineTerminator}                        {/* ignora los saltos de linea */}

    /* error fallback */
    [^]                                     { errores.agregarError("Lexico",yytext(),"Caracter no aceptado",new Pos(yyline+1, yycolumn+1)); }

}

<STRING> {
    "P"                                     { cadena.append("P"); }
    "J"                                     { cadena.append("J"); }
    "V"                                     { cadena.append("V"); }
    "PY"                                    {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.PY); 
                                                }
                                            }
    "JAVA."                                  {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.CADENA);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.JAVA); 
                                                }
                                            }
    "VB"                                    {   if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    yypushback(yylength());
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, sym.VB);
                                                }else{
                                                    return symbol(yyline+1, yycolumn+1, yytext(), sym.VB); 
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
    "\""                                    {   cambiarEstado();  
                                                if(cadena.length() > 0){
                                                    String lexema = cadena.toString();
                                                    cadena.setLength(0);
                                                    return symbol(yyline+1, (yycolumn+1)-lexema.length(), lexema, (lexema.length() > 1)? sym.CADENA : sym.CARACTER);
                                                }
                                            }
    [^"\"""\%""P""J""V"]                    { cadena.append(yytext()); }
}

    [^]                                     { errores.agregarError("Lexico",yytext(),"Caracter no aceptado",new Pos(yyline+1, yycolumn+1)); }