/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

import com.analisis.objetos.analisis.CONST;
import com.generadores.CodigoAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada para la manipulacion de los cuartetos generados a lo largo del proyecto
 * @author jose_
 */
public class Cuartetos {
    
    public static int noActual = 5;
    
    public static void cambiarEtiqueta(List<Cuarteto> cuartetos, String etiquetaAnterior, String nuevaEtiqueta){
        for (Cuarteto cuarteto : cuartetos) {
            String op = cuarteto.getOp();
            if(op.equals("goto")||op.equals(">")||op.equals("<")||op.equals(">=")||op.equals("<=")||op.equals("==")||op.equals("!=")){
                if(cuarteto.getRes().equals(etiquetaAnterior))cuarteto.setRes(nuevaEtiqueta);
            }
        }
    }
    
    public static void unirCuartetos(List<Cuarteto> cuartetos, List<Cuarteto> cuartetosUnir){
        for (Cuarteto cuarteto : cuartetosUnir) {
            cuartetos.add(cuarteto);
        }
    }
    
    private static String tipoDeC(String tipo){
        switch((tipo==null)?CONST.INDEFINIDO:tipo){
            case CONST.ENTERO: return "int";
            case CONST.FLOTANTE: return "float";
            case CONST.CARACTER: return "char";
            case CONST.INDEFINIDO: return "float";
        }
        return null;
    }
    
    private static String tipoDeComodin(String tipo){
        switch((tipo==null)?"Indefinido":tipo){
            case CONST.ENTERO: return "%d";
            case CONST.FLOTANTE: return "%f";
            case CONST.CARACTER: return " %c";
        }
        return null;
    }
    
    public static String escribirCodigo3DireccionesEjecutable(List<Cuarteto> cuartetos){
        String codigo = "#include <stdio.h>\n\n"
                + "float stack[10000];\nfloat heap[10000];\nint p = 0;\nint h = 0;\nint t00;\n";
        for (VarT temporal : Temporal.temporales) {
            codigo+=tipoDeC(temporal.getTipo())+" "+temporal.getTemporal()+";\n";
        }
        codigo+="\n";
        for (Cuarteto cuarteto : cuartetos) {
            if(cuarteto.getOp().equals("metodo")) codigo+="void "+cuarteto.getRes()+"(void);"+"\n";
        }
        codigo+="\n";
        for (Cuarteto cuarteto : cuartetos) {
            switch(cuarteto.getOp()){
                case "metodo":{
                    codigo+="void "+cuarteto.getRes()+"(void){ \n";
                    break;
                }
                case "main":{
                    cuarteto.setRes("main");
                    codigo+="int main(void){\n";
                    break;
                }
                case "finMetodo":{
                    codigo+="}\n";
                    break;
                }
                case "return":{
                    codigo+="return 0;\n";
                    break;
                }
                case "call":{
                    codigo+=cuarteto.getIz()+"();\n";
                    break;
                }
                case "+":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" + "+cuarteto.getDer()+";\n";
                    break;
                }
                case "-":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" - "+cuarteto.getDer()+";\n";
                    break;
                }
                case "*":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" * "+cuarteto.getDer()+";\n";
                    break;
                }
                case "/":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" / "+cuarteto.getDer()+";\n";
                    break;
                }
                case "%":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" % "+cuarteto.getDer()+";\n";
                    break;
                }
                case "==":{
                    codigo+="if ("+cuarteto.getIz()+" == "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case "<=":{
                    codigo+="if ("+cuarteto.getIz()+" <= "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case ">=":{
                    codigo+="if ("+cuarteto.getIz()+" >= "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case "<":{
                    codigo+="if ("+cuarteto.getIz()+" < "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case ">":{
                    codigo+="if ("+cuarteto.getIz()+" > "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case "!=":{
                    codigo+="if ("+cuarteto.getIz()+" != "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case ":=":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+";\n";
                    break;
                }
                case ":=a":{
                    String tipo = tipoDeC(Temporal.getTipoTemporal(cuarteto.getDer()));
                    codigo+=cuarteto.getRes()+"["+(((tipo.equals("int"))?"":"(int)"))+cuarteto.getDer()+"]"+" = (float) "+cuarteto.getIz()+";\n";
                    break;
                }
                case "arreglo":{
                    String tipo = Temporal.getTipoTemporal(cuarteto.getRes());
                    String tipo2 = Temporal.getTipoTemporal(cuarteto.getDer());
                    codigo+=cuarteto.getRes()+" = ("+tipoDeC(tipo)+")"+cuarteto.getIz()+"["+(((tipoDeC(tipo2).equals("int"))?"":"(int)"))+cuarteto.getDer()+"];\n";
                    break;
                }
                case "etiqueta":{
                    codigo+=cuarteto.getRes()+":\n";
                    break;
                }
                case "goto":{
                    codigo+="goto "+cuarteto.getRes()+";\n";
                    break;
                }
                case "vacio":{
                    codigo+="\n";
                    break;
                }
                case "read":{
                    String tipo = Temporal.getTipoTemporal(cuarteto.getRes());
                    String ubicacion = ((cuarteto.getRes()!=null)?cuarteto.getRes():null);
                    if(ubicacion==null){
                        codigo+="getchar();\n";
                    }else{
                        switch(tipo){
                            case CONST.ENTERO:{ codigo+="scanf(\"%d\",&"+ubicacion+");\n"; break; }
                            case CONST.FLOTANTE:{ codigo+="scanf(\"%f\",&"+ubicacion+");\n"; break; }
                            case CONST.CARACTER:{ codigo+="scanf(\"%c\",&"+ubicacion+");\n"; break; }
                        }
                    }
                    break;
                }
                case "printCadena":{
                    codigo+="printf(\""+cuarteto.getRes()+"\");\n";
                    break;
                }
                case "printDato":{
                    String tipo = tipoDeC(Temporal.getTipoTemporal(cuarteto.getRes()));
                    String comodin = tipoDeComodin(Temporal.getTipoTemporal(cuarteto.getRes()));
                    codigo+="printf(\""+comodin+"\",("+tipo+")"+cuarteto.getRes()+");\n";
                    break;
                }
            }
        }
        return codigo;
    }

    public static String escribirCodigo3DireccionesNormal(List<Cuarteto> cuartetos) {
        String codigo = "";
        for (Cuarteto cuarteto : cuartetos) {
            switch(cuarteto.getOp()){
                case "metodo":{
                    codigo+="void "+cuarteto.getRes()+" { \n";
                    break;
                }
                case "finMetodo":{
                    codigo+="}\n";
                    break;
                }
                case "call":{
                    codigo+="call "+cuarteto.getIz()+"\n";
                    break;
                }
                case "+":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" + "+cuarteto.getDer()+"\n";
                    break;
                }
                case "-":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" - "+cuarteto.getDer()+"\n";
                    break;
                }
                case "*":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" * "+cuarteto.getDer()+"\n";
                    break;
                }
                case "/":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" / "+cuarteto.getDer()+"\n";
                    break;
                }
                case "%":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+" % "+cuarteto.getDer()+"\n";
                    break;
                }
                case "param":{
                    codigo+="param "+cuarteto.getRes()+"\n";
                    break;
                }
                case "==":{
                    codigo+="if "+cuarteto.getIz()+" = "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case "<=":{
                    codigo+="if "+cuarteto.getIz()+" <= "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case ">=":{
                    codigo+="if "+cuarteto.getIz()+" >= "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case "<":{
                    codigo+="if "+cuarteto.getIz()+" < "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case ">":{
                    codigo+="if "+cuarteto.getIz()+" > "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case "!=":{
                    codigo+="if "+cuarteto.getIz()+" != "+cuarteto.getDer()+" goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case ":=":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+"\n";
                    break;
                }
                case ":=a":{
                    codigo+=cuarteto.getRes()+"["+cuarteto.getDer()+"]"+" = "+cuarteto.getIz()+"\n";
                    break;
                }
                case "arreglo":{
                    codigo+=cuarteto.getRes()+" = "+cuarteto.getIz()+"["+cuarteto.getDer()+"]\n";
                    break;
                }
                case "dclArreglo":{
                    codigo+=cuarteto.getRes()+"["+cuarteto.getDer()+"]\n";
                    break;
                }
                case "etiqueta":{
                    codigo+=cuarteto.getRes()+":\n";
                    break;
                }
                case "goto":{
                    codigo+="goto "+cuarteto.getRes()+"\n";
                    break;
                }
                case "vacio":{
                    codigo+="\n";
                    break;
                }
                case "read":{
                    if(cuarteto.getDer()!=null){
                        codigo+="read "+cuarteto.getRes()+"["+cuarteto.getDer()+"]"+"\n";
                    }else{
                        codigo+="read "+((cuarteto.getRes()!=null)?cuarteto.getRes():"")+"\n";
                    }
                    break;
                }
                case "printCadena":{
                    codigo+="print \""+cuarteto.getRes()+"\"\n";
                    break;
                }
                case "printDato":{
                    codigo+="print "+cuarteto.getRes()+"\n";
                    break;
                }
                case "print":{
                    codigo+="print "+cuarteto.getRes()+"\n";
                    break;
                }
            }
        }
        return codigo;
    }
    
    public static String escribirCodigoAssembler(List<Cuarteto> cuartetos){
        noActual = 5;
        String codigo = "\t.file \"codigoC.c\"\n"
                + "\t.text\n"
                + "\t.globl stack\n"
                + "\t.bss\n"
                + "\t.align 32\n"
                + "\t.type  stack, @object\n"
                + "\t.size  stack, 40000\n"
                + "stack:\n"
                + "\t.zero  40000\n"
                + "\t.globl heap\n"
                + "\t.align 32\n"
                + "\t.type  heap, @object\n"
                + "\t.size  heap, 40000\n"
                + "heap:\n"
                + "\t.zero  40000\n"
                + "\t.globl p\n"
                + "\t.align 4\n"
                + "\t.type  p, @object\n"
                + "\t.size  p, 4\n"
                + "p:\n"
                + "\t.zero  4\n"
                + "\t.globl h\n"
                + "\t.align 4\n"
                + "\t.type  h, @object\n"
                + "\t.size  h, 4\n"
                + "h:\n"
                + "\t.zero  4\n"
                + "\t.globl t00\n"
                + "\t.align 4\n"
                + "\t.type  t00, @object\n"
                + "\t.size  t00, 4\n"
                + "t00:\n"
                + "\t.zero  4\n";
        for (VarT temporal : Temporal.temporales) {
            codigo+="\t.globl "+temporal.getTemporal()+"\n"
                + (!(temporal.getTipo().equals(CONST.CARACTER))?"\t.align 4\n":"")
                + "\t.type  "+temporal.getTemporal()+", @object\n"
                + "\t.size  "+temporal.getTemporal()+", "+((temporal.getTipo().equals(CONST.CARACTER))? "1" : "4")+"\n"
                + "t00:\n"
                + "\t.zero  "+((temporal.getTipo().equals(CONST.CARACTER))? "1" : "4")+"\n";
        }
        declaracionDeMetodos : {
            List<Cuarteto> cuartetosDeMetodo = new ArrayList();
            int noMetodo = 0;
            boolean dentroDeMetodo = true;
            for (int i = 0; i < cuartetos.size(); i++) {
                if(!cuartetos.get(i).getOp().equals("finMetodo")){
                    cuartetosDeMetodo.add(cuartetos.get(i));
                }else{
                    dentroDeMetodo = false;
                }
                if(!dentroDeMetodo){
                    codigo+=codigoDeMetodo(cuartetosDeMetodo, cuartetosDeMetodo.get(0).getRes(), noMetodo++);
                    dentroDeMetodo = true;
                }
            }
        }
        
        return codigo;
    }
    
    private static String codigoDeMetodo(List<Cuarteto> cuartetos, String id, int noMetodo){
        String codigo = "";
        String declaracionFlotantes = "";
        
        generacionDeCadenas :{
           
            if(id.equals("main"))codigo+=".LC1:\n"
                    + "\t.string\t\" %c\"\n"
                    + ".LC2:\n"
                    + "\t.string\t\"%c\"\n"
                    + ".LC3:\n"
                    + "\t.string\t\"%d\"\n"
                    + ".LC4:\n"
                    + "\t.string\t\"%f\"\n";
            for (int i = 0; i < cuartetos.size(); i++) {
                Cuarteto cuarteto = cuartetos.get(i);
                switch(cuarteto.getOp()){
                    case "printCadena":{
                        codigo+=".LC"+noActual+":\n\t.string \""+cuarteto.getRes()+"\"\n";
                        cuarteto.setRes("LC"+(noActual++));
                        break;
                    }
                    case "printDato":{
                        String tipo = tipoDeC(Temporal.getTipoTemporal(cuarteto.getRes()));
                        cuarteto.setDer(tipo);
                        break;
                    }
                    case ":=":{
                        String tipo = Temporal.getTipoTemporal(cuarteto.getRes());
                        if(tipo.equals(CONST.FLOTANTE)){
                            declaracionFlotantes+="\t.align 4\n"
                                    + ".LC"+noActual+":\n"
                                    + "\t.long\t"+Float.floatToRawIntBits(Float.parseFloat(cuarteto.getIz()))+"\n";
                        }
                        cuarteto.setIz("LC"+(noActual++));
                    }
                }
            }

        }
        codigo = ((!id.equals("main"))?"\t.text\n":"")
                + "\t.globl   "+id+"\n"
                + "\t.type    "+id+", @function\n"
                + id+":\n"
                + ".LFB"+noMetodo+":\n"
                + "\t.cfi_startproc\n"
                + "\tpushq\t%rbp\n"
                + "\t.cfi_def_cfa_offset 16\n"
                + "\t.cfi_offset 6, -16\n"
                + "\tmovq\t%rsp, %rbp\n"
                + "\t.cfi_def_cfa_register 6\n";
        
        generacionDeCodigo: {
            
            for (int i = 1; i < cuartetos.size(); i++) {
                Cuarteto cuarteto = cuartetos.get(i);
                switch(cuarteto.getOp()){
                    case "finMetodo":{
                        codigo+="\tnop\n"
                            + "\tpopq\t%rbp\n"
                            + "\t.cfi_def_cfa 7, 8\n"
                            + "\tret\n"
                            + "\t.cfi_endproc\n"
                            + ".LFE"+noMetodo;
                        if(id.equals("main")){
                            codigo+="\t.size\tmain, .-main\n";
                        }
                    }
                    case "call":{
                        codigo+="\tcall\t"+cuarteto.getIz()+"\n";
                        break;
                    }
                    case "+":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+" + "+cuarteto.getDer()+";\n";
                        codigo+= generador.generarCodigoDeSuma(cuarteto);
                        break;
                    }
                    case "-":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+" - "+cuarteto.getDer()+";\n";
                        codigo+= generador.generarCodigoDeResta(cuarteto);
                        break;
                    }
                    case "*":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+" * "+cuarteto.getDer()+";\n";
                        codigo+= generador.generarCodigoDeMultiplicacion(cuarteto);
                        break;
                    }
                    case "/":{CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+" / "+cuarteto.getDer()+";\n";
                        codigo+= generador.generarCodigoDeDivision(cuarteto);
                        break;
                    }
                    case "%":{CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+" % "+cuarteto.getDer()+";\n";
                        codigo+= generador.generarCodigoDeModulo(cuarteto);
                        break;
                    }
                    case "==":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" == "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case "<=":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" <= "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case ">=":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" >= "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case "<":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" < "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case ">":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" > "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case "!=":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//if ("+cuarteto.getIz()+" != "+cuarteto.getDer()+") goto "+cuarteto.getRes()+";\n";
                        codigo+= generador.generarCodigoDeCondicional(cuarteto);
                        break;
                    }
                    case ":=":{
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = "+cuarteto.getIz()+";\n";
                        codigo+= generador.generarCodigoDeAsignacion(cuarteto);
                        break;
                    }
                    case ":=a":{
                        String tipo = tipoDeC(Temporal.getTipoTemporal(cuarteto.getDer()));
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+"["+(((tipo.equals("int"))?"":"(int)"))+cuarteto.getDer()+"]"+" = (float) "+cuarteto.getIz()+";\n";
                        codigo+= generador.generarCodigoDeAsignacion(cuarteto);
                        break;
                    }
                    case "arreglo":{
                        String tipo = Temporal.getTipoTemporal(cuarteto.getRes());
                        String tipo2 = Temporal.getTipoTemporal(cuarteto.getDer());
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+="//"+cuarteto.getRes()+" = ("+tipoDeC(tipo)+")"+cuarteto.getIz()+"["+(((tipoDeC(tipo2).equals("int"))?"":"(int)"))+cuarteto.getDer()+"];\n";
                        codigo+= generador.generarCodigoDeAsignacion(cuarteto);
                        break;
                    }
                    case "etiqueta":{
                        codigo+="//"+cuarteto.getRes()+":\n";
                        codigo+="."+cuarteto.getRes()+":\n";
                        break;
                    }
                    case "goto":{
                        codigo+="//goto "+cuarteto.getRes()+";\n";
                        codigo+="\tjmp\t"+cuarteto.getRes()+"\n";
                        break;
                    }
                    case "vacio":{
                        codigo+="\n";
                        break;
                    }
                    case "read":{
                        String tipo = Temporal.getTipoTemporal(cuarteto.getRes());
                        String ubicacion = ((cuarteto.getRes()!=null)?cuarteto.getRes():null);
                        if(ubicacion==null){
                            codigo+="getchar();\n";
                        }else{
                            switch(tipo){
                                case CONST.ENTERO:{ codigo+="//scanf(\"%d\",&"+ubicacion+");\n"; break; }
                                case CONST.FLOTANTE:{ codigo+="//scanf(\"%f\",&"+ubicacion+");\n"; break; }
                                case CONST.CARACTER:{ codigo+="//scanf(\" %c\",&"+ubicacion+");\n"; break; }
                            }
                        }
                        CodigoAssembler generador = new CodigoAssembler();
                        codigo+= generador.generarCodigoDeScanf(cuarteto);
                        break;
                    }
                    case "printCadena":{
                        codigo+="//printf(\""+cuarteto.getRes()+"\");\n";
                        codigo+="\tleaq\t."+cuarteto.getRes()+"(%rip), %rdi\n"
                                + "\tmovl\t$0, %eax\n"
                                + "\tcall\tprint@PLT\n";
                        break;
                    }
                    case "printDato":{
                        String comodin = tipoDeComodin(Temporal.getTipoTemporal(cuarteto.getRes()));
                        codigo+="//printf(\""+comodin+"\",("+cuarteto.getDer()+")"+cuarteto.getRes()+");\n";
                        switch(comodin){
                            case " %c":{
                                codigo+="\tmovzbl\t"+cuarteto.getRes()+"(%rip), %eax\n"
                                    +"\tmovsbl\t%al, %eax\n"
                                    +"\tmovl\t%eax, %esi\n"
                                    +"\tleaq\t.LC1(%rip), %rdi\n"
                                    +"\tmovl\t$0, %eax\n"
                                    +"\tcall\tprintf@PLT\n";
                                break;
                            }
                            case "%d":{
                                codigo+="\tmovl\t"+cuarteto.getRes()+"(%rip), %eax\n"
                                    +"\tmovl\t%eax, %esi\n"
                                    +"\tleaq\t.LC3(%rip), %rdi\n"
                                    +"\tmovl\t$0, %eax\n"
                                    +"\tcall\tprintf@PLT\n";
                                break;
                            }
                            case "%f":{
                                codigo+="\tmovss\t"+cuarteto.getRes()+"(%rip), %xmm0\n"
                                    +"\tpxor\t%xmm2, %xmm2\n"
                                    +"\tcvtss2sd\t%xmm0, %xmm2\n"
                                    +"\tmovq\t%xmm2, %rax\n"
                                    +"\tmovq\t%rax, %xmm0\n"
                                    +"\tleaq\t.LC4(%rip), %rdi\n"
                                    +"\tmovl\t$1, %eax\n"
                                    +"\tcall\tprintf@PLT\n";
                                break;
                            }
                        }        
                        break;
                    }
                }
            }
            
        }
        
        codigo+=declaracionFlotantes+"\t.ident \"GCC: (GNU) 10.2.0\n"
                                    + "\t.section\t.note.GNU-stack,\"\",@progbits";;
        
        
        return codigo;
    }

    public static void eliminarRedundanciaEtiquetas(List<Cuarteto> cuartetos) {
        for (int i = 0; i < cuartetos.size(); i++) {
            if(cuartetos.get(i).getOp().equals("etiqueta")){
                int j = i+1;
                int etiquetasSeguidas = 0;
                while((j<cuartetos.size())&&cuartetos.get(j++).getOp().equals("etiqueta")) etiquetasSeguidas++;
                eliminarEtiquetas(i,etiquetasSeguidas,cuartetos);
            }
        }
    }

    private static void eliminarEtiquetas(int i, int etiquetasSeguidas, List<Cuarteto> cuartetos) {
        String etiquetaQueSeQueda = cuartetos.get(i).getRes();
        for (int j = 0; j < etiquetasSeguidas; j++) {
            cambiarEtiqueta(cuartetos, cuartetos.get(i+1).getRes(), etiquetaQueSeQueda);
            cuartetos.remove(i+1);
        }
    }
}
