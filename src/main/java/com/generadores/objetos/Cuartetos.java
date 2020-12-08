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
    
    public void cambiarEtiqueta(List<Cuarteto> cuartetos, String etiquetaAnterior, String nuevaEtiqueta){
        for (Cuarteto cuarteto : cuartetos) {
            if(cuarteto.getRes().equals(etiquetaAnterior)) cuarteto.setRes(nuevaEtiqueta);
        }
    }
    
    public void unirCuartetos(List<Cuarteto> cuartetos, List<Cuarteto> cuartetosUnir){
        for (Cuarteto cuarteto : cuartetosUnir) {
            cuartetos.add(cuarteto);
        }
    }
    
    private String tipoDeC(String tipo){
        switch((tipo==null)?"indefinido----":tipo){
            case CONST.ENTERO: return "int";
            case CONST.FLOTANTE: return "float";
            case CONST.CARACTER: return "char";
        }
        return null;
    }
    
    private String tipoDeComodin(String tipo){
        switch((tipo==null)?"Indefinido----":tipo){
            case CONST.ENTERO: return "%d";
            case CONST.FLOTANTE: return "%f";
            case CONST.CARACTER: return " %c";
        }
        return null;
    }
}
