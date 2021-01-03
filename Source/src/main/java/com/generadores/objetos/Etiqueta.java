/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

/**
 * Clase destinada a la manipulacion de las etiquetas para el codigo 3 direcciones
 * @author jose_
 */
public class Etiqueta {
    
    public static String etiqueta = "et";
    public static int contador = 0;
    
    public static String siguienteEtiqueta(){
        etiqueta = "et"+contador;
        contador++;
        return etiqueta;
    }
    
    public static String ultimaEtiqueta(){
        return etiqueta;
    }
    
    public static void reiniciar(){
        contador=0;
    }
    
}
