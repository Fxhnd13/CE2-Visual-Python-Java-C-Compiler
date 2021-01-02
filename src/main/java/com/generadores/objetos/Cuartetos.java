/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

import com.analisis.objetos.analisis.CONST;
import java.util.List;

/**
 * Clase destinada para la manipulacion de los cuartetos generados a lo largo del proyecto
 * @author jose_
 */
public class Cuartetos {
    
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
            switch(cuarteto.getOp()){
                case "metodo":{
                    codigo+="void "+cuarteto.getRes()+"(void){ \n";
                    break;
                }
                case "main":{
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

    public static void eliminarRedundanciaEtiquetas(List<Cuarteto> cuartetos) {
//        for (int i = 0; i < cuartetos.size(); i++) {
//            if(cuartetos.get(i).getOp().equals("etiqueta")){
//                int j = i+1;
//                int etiquetasSeguidas = 0;
//                while((j<cuartetos.size())&&cuartetos.get(j++).getOp().equals("etiqueta")) etiquetasSeguidas++;
//                eliminarEtiquetas(i,etiquetasSeguidas,cuartetos);
//            }
//        }
    }

    private static void eliminarEtiquetas(int i, int etiquetasSeguidas, List<Cuarteto> cuartetos) {
        String etiquetaQueSeQueda = cuartetos.get(i).getRes();
        for (int j = 0; j < etiquetasSeguidas; j++) {
            cambiarEtiqueta(cuartetos, cuartetos.get(i+1).getRes(), etiquetaQueSeQueda);
            cuartetos.remove(i+1);
        }
    }
}
