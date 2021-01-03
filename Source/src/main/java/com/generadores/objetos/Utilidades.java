/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.generadores.objetos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Llamadas.LlamadaJava;
import com.analisis.objetos.basicos.Llamadas.LlamadaPython;
import com.analisis.objetos.basicos.Llamadas.LlamadaVisual;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Metodo;
import com.analisis.objetos.estructuras.TablaDeSimbolos;
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
        String nombre = seccion+"_"+simboloMetodo.getId()+((metodo).getParametros().isEmpty()?"":"_");
        for (int i = 0; i < metodo.getParametros().size(); i++) {
            nombre+= metodo.getParametros().get(i).getTipo();
            if((i+1) < metodo.getParametros().size()) nombre+="_";
        }
        return nombre;
    }
    
    public static String nombreMetodo(String seccion, Llamada metodo){
        String nombre = seccion+"_"+metodo.getIdMetodo()+(metodo.getParametros().isEmpty()?"":"_");
        for (int i = 0; i < metodo.getParametros().size(); i++) {
            nombre+= (seccion.equals(CONST.SEC_PY))? CONST.INDEFINIDO : metodo.getParametros().get(i).getTipoRetorno();
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
    
    

    public static boolean existeMetodo(TablaDeSimbolos metodos, Simbolo metodo) {
        for (Simbolo metodoT : metodos.getSimbolos()) {
            MetodoInstr metodoComparacion = (MetodoInstr) metodoT.getValor();
            MetodoInstr metodoComparador = (MetodoInstr) metodo.getValor();
            if(metodoComparacion.getId().equals(metodoComparador.getId())){
                boolean existe = true;
                for (int i = 0; i < metodoComparacion.getParametros().size(); i++) {    
                    if(!metodoComparacion.getParametros().get(i).equals(metodoComparador.getParametros().get(i))){
                        existe = false;
                    }
                }
                if(existe) return true;
            }
        }
        return false;
    }

    public static Simbolo existeMetodo(TablaDeSimbolos metodosVb, LlamadaVisual llamada) {
        System.out.println("-----------LLAMADA---------------");
        System.out.println("Metodo LLamado: "+nombreMetodo(CONST.SEC_VB, llamada));
        System.out.println("Metodos encontrados: ");
        for (Simbolo metodo : metodosVb.getSimbolos()) {
            System.out.println("    "+nombreMetodo(CONST.SEC_VB,((MetodoInstr)metodo.getValor())));
        }
        for (Simbolo metodo : metodosVb.getSimbolos()) {
            MetodoInstr instr = (MetodoInstr) metodo.getValor();
            if(nombreMetodo(CONST.SEC_VB, instr).equals(nombreMetodo(CONST.SEC_VB, llamada))) return metodo;
        }
        return null;
    }
    
    public static Simbolo existeMetodo(TablaDeSimbolos metodosPy, LlamadaPython llamada){
        System.out.println("-----------LLAMADA---------------");
        System.out.println("Metodo LLamado: "+nombreMetodo(CONST.SEC_PY, llamada));
        System.out.println("Metodos encontrados: ");
        for (Simbolo metodo : metodosPy.getSimbolos()) {
            System.out.println("    "+nombreMetodo(CONST.SEC_PY,((MetodoInstr)metodo.getValor())));
        }
        for (Simbolo metodo : metodosPy.getSimbolos()) {
            MetodoInstr instr = (MetodoInstr) metodo.getValor();
            if(nombreMetodo(CONST.SEC_PY, instr).equals(nombreMetodo(CONST.SEC_PY, llamada))) return metodo;
        }
        return null;
    }
    
    public static Simbolo existeMetodo(TablaDeSimbolos metodos, String clase, LlamadaJava llamada){
        System.out.println("-----------LLAMADA---------------");
        System.out.println("Metodo LLamado: "+nombreMetodo(CONST.SEC_JV,clase,llamada));
        System.out.println("Metodos encontrados: ");
        for (Simbolo metodo : metodos.getSimbolos()) {
            System.out.println("    "+nombreMetodo(CONST.SEC_JV,clase,((MetodoInstr)metodo.getValor())));
        }
        for (Simbolo metodo : metodos.getSimbolos()) {
            MetodoInstr instr = (MetodoInstr) metodo.getValor();
            if(nombreMetodo(CONST.SEC_JV,clase,instr).equals(nombreMetodo(CONST.SEC_JV,clase,llamada))) return metodo;
        }
        return null;
    }
    
    public static Simbolo existeMetodo(String seccion, TablaDeSimbolos metodos, String clase, Llamada llamada){
        System.out.println("-----------LLAMADA---------------");
        System.out.println("Metodo LLamado: "+nombreMetodo(seccion,clase,llamada));
        System.out.println("Metodos encontrados: ");
        for (Simbolo metodo : metodos.getSimbolos()) {
            System.out.println("    "+nombreMetodo(seccion,clase,((MetodoInstr)metodo.getValor())));
        }
        for (Simbolo metodo : metodos.getSimbolos()) {
            MetodoInstr instr = (MetodoInstr) metodo.getValor();
            if(nombreMetodo(seccion,clase,instr).equals(nombreMetodo(seccion,clase,llamada))) {
                return metodo;
            }
        }
        return null;
    }
}
