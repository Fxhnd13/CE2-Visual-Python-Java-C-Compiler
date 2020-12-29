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
            if(cuarteto.getRes().equals(etiquetaAnterior)) cuarteto.setRes(nuevaEtiqueta);
        }
    }
    
    public static void unirCuartetos(List<Cuarteto> cuartetos, List<Cuarteto> cuartetosUnir){
        for (Cuarteto cuarteto : cuartetosUnir) {
            cuartetos.add(cuarteto);
        }
    }
    
    private static String tipoDeC(String tipo){
        switch((tipo==null)?"indefinido----":tipo){
            case CONST.ENTERO: return "int";
            case CONST.FLOTANTE: return "float";
            case CONST.CARACTER: return "char";
        }
        return null;
    }
    
    private static String tipoDeComodin(String tipo){
        switch((tipo==null)?"Indefinido----":tipo){
            case CONST.ENTERO: return "%d";
            case CONST.FLOTANTE: return "%f";
            case CONST.CARACTER: return " %c";
        }
        return null;
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
                    codigo+=cuarteto.getIz()+"\n";
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
                case "return":{
                    codigo+="return "+cuarteto.getRes()+"\n";
                    break;
                }
            }
        }
        return codigo;
    }
}
