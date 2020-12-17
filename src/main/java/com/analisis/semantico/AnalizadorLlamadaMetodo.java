/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.semantico;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Llamadas.LlamadaJava;
import com.analisis.objetos.basicos.Llamadas.LlamadaPython;
import com.analisis.objetos.basicos.Llamadas.LlamadaVisual;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Utilidades;

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
        }else{ //por si se hace una llamada de ambito
            String tipoRetorno = null;
            switch(coleccion.getTipadoActual()){
                case 0: {
                    tipoRetorno = analizarLlamadaMetodoVisualConReturn(new LlamadaVisual(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion);
                    break;
                }
                case 1: {
                    for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
                    Clase estructura = (Clase) coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor();
                    Simbolo metodoLlamado = Utilidades.existeMetodo(CONST.SEC_JV, estructura.getMetodos(), coleccion.getClase(), llamada);
                    if(metodoLlamado==null){
                        coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
                    }else{
                        if(metodoLlamado.getTipo().equals(CONST.VOID)){
                            coleccion.getErrores().agregarError("Semantico", llamada.getIdMetodo(), "El metodo llamado no posee un valor de retorno.", llamada.getPosicion());
                        }else{
                            tipoRetorno = metodoLlamado.getTipo();
                        }
                    }
                    break;
                }
                case 2: {
                    tipoRetorno = analizarLlamadaMetodoPython(new LlamadaPython(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion);
                    break;
                }
            }
            return tipoRetorno;
        }
    }
    
    public String analizarLlamadaMetodoJavaConReturn(LlamadaJava llamada, Coleccion coleccion) {
        String tipoRetorno = null;
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(llamada.getIdVariable());
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        if(simbolo!=null){
            Clase estructura = (Clase) coleccion.getClasesJv().getSimbolo(simbolo.getTipo()).getValor();
            Simbolo metodoLlamado = Utilidades.existeMetodo(estructura.getMetodos(), simbolo.getTipo(), llamada);
            if(metodoLlamado==null){
                coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
            }else{
                if(metodoLlamado.getTipo().equals(CONST.VOID)){
                    coleccion.getErrores().agregarError("Semantico", llamada.getIdMetodo(), "El metodo llamado no posee un valor de retorno.", llamada.getPosicion());
                }else{
                    tipoRetorno = metodoLlamado.getTipo();
                }
            }
        }
        return tipoRetorno;
    }

    public String analizarLlamadaMetodoPython(LlamadaPython llamada, Coleccion coleccion) {
        String tipoRetorno = CONST.INDEFINIDO;
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        Simbolo metodoLlamado = Utilidades.existeMetodo(coleccion.getMetodosVb(), llamada);
        if(metodoLlamado==null){
            coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
        }
        return tipoRetorno;
    }

    public String analizarLlamadaMetodoVisualConReturn(LlamadaVisual llamada, Coleccion coleccion) {
        String tipoRetorno = null;
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        Simbolo metodoLlamado = Utilidades.existeMetodo(coleccion.getMetodosVb(), llamada);
        if(metodoLlamado==null){
            coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
        }else{
            if(metodoLlamado.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico", llamada.getIdMetodo(), "El metodo llamado no posee un valor de retorno.", llamada.getPosicion());
            }else{
                tipoRetorno = metodoLlamado.getTipo();
            }
        }
        return tipoRetorno;
    }
    
}
