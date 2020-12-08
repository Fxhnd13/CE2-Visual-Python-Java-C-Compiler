/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.generadores.objetos;

import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Metodo;
import com.analisis.objetos.instrucciones.instruccionesmlg.MetodoInstr;

/**
 *
 * @author Jose Soberanis
 */
public class Utilidades {

    public static String nombreMetodo(String seccion, MetodoInstr metodo){
        String nombre = seccion+"_"+metodo.getId()+((metodo.getParametros().isEmpty())?"":"_");
        for (int i = 0; i < metodo.getParametros().size(); i++) {
            nombre += metodo.getParametros().get(i).getTipo();
            if((i+1) < metodo.getParametros().size()) nombre+="_";
        }
        return nombre;
    }
    
    public static String nombreMetodo(String seccion, Simbolo simboloMetodo){
        Metodo metodo = (Metodo)simboloMetodo.getValor();
        String nombre = seccion+"_"+simboloMetodo.getId()+(!(metodo).getParametros().isEmpty()?"":"_");
        for (int i = 0; i < metodo.getParametros().size(); i++) {
            nombre+= metodo.getParametros().get(i).getTipo();
            if((i+1) < metodo.getParametros().size()) nombre+="_";
        }
        return nombre;
    }
    
    public static String nombreMetodo(String seccion, Llamada metodo){
        String nombre = seccion+"_"+metodo.getIdMetodo()+(!metodo.getParametros().isEmpty()?"":"_");
        for (int i = 0; i < metodo.getParametros().size(); i++) {
            nombre+= metodo.getParametros().get(i).getTipo();
            if((i+1) < metodo.getParametros().size()) nombre+="_";
        }
        return nombre;
    }
    
    public static String nombreMetodo(String seccion, String clase, Simbolo simboloMetodo){
        seccion+="_"+clase;
        return nombreMetodo(seccion, simboloMetodo);
    }
    
    public static String nombreMetodo(String seccion, String clase, MetodoInstr metodo){
        seccion+="_"+clase;
        return nombreMetodo(seccion, metodo);
    }
    
    public static String nombreMetodo(String seccion, String clase, Llamada metodo){
        seccion+="_"+clase;
        return nombreMetodo(seccion, metodo);
    }
}
