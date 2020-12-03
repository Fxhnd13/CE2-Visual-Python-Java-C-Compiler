/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.semantico;

import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Llamadas.LlamadaJava;
import com.analisis.objetos.basicos.Llamadas.LlamadaPython;
import com.analisis.objetos.basicos.Llamadas.LlamadaVisual;
import com.analisis.objetos.estructuras.Coleccion;

/**
 *
 * @author jose_
 */
public class AnalizadorLlamadaMetodo {

    public String analizarLLamada(Llamada llamada, Coleccion coleccion) {
        if(llamada instanceof LlamadaJava){
            return analizarLlamadaMetodoJavaConReturn((LlamadaJava)llamada, coleccion);
        }else if(llamada instanceof LlamadaPython){
            return analizarLlamadaMetodoPython((LlamadaPython)llamada, coleccion);
        }else if(llamada instanceof LlamadaVisual){
            return analizarLlamadaMetodoVisualConReturn((LlamadaVisual)llamada, coleccion);
        }else{
            String tipoRetorno = null;
            switch(coleccion.getTipadoActual()){
                case 0: {
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
            }
            return tipoRetorno;
        }
    }
    
    public String analizarLlamadaMetodoJavaConReturn(LlamadaJava llamada, Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String analizarLlamadaMetodoPython(LlamadaPython llamada, Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String analizarLlamadaMetodoVisualConReturn(LlamadaVisual llamada, Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
