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

    public void analizarLlamadaSinReturn(Llamada llamada, Coleccion coleccion){
        if(llamada instanceof LlamadaJava){
            analizarLlamadaMetodoJavaSinReturn((LlamadaJava)llamada, coleccion);
        }else if(llamada instanceof LlamadaPython){
            analizarLlamadaMetodoPythonSinReturn((LlamadaPython)llamada, coleccion);
        }else if(llamada instanceof LlamadaVisual){
            analizarLlamadaMetodoVisualSinReturn((LlamadaVisual)llamada, coleccion);
        }else{
            switch(coleccion.getTipadoActual()){
                case 0: {
                    analizarLlamadaMetodoVisualSinReturn(new LlamadaVisual(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion);
                    break;
                }
                case 1: {
                    for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
                    Clase estructura = (Clase) coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor();
                    Simbolo metodoLlamado = Utilidades.existeMetodo(CONST.SEC_JV, estructura.getMetodos(), coleccion.getClase(), llamada);
                    if(metodoLlamado==null){
                        coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
                    }
                    break;
                }
                case 2: {
                    analizarLlamadaMetodoPythonSinReturn(new LlamadaPython(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion);
                    break;
                }
            }
        }
    }
    
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
            if(simbolo.getValor()==null){
                coleccion.getErrores().agregarError("Semantico", llamada.getIdVariable(), "No se ha instanciado el objeto para el cual se realizo una llamada de un metodo", llamada.getPosicion());
            }else{
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
        }
        return tipoRetorno;
    }

    public String analizarLlamadaMetodoPython(LlamadaPython llamada, Coleccion coleccion) {
        String tipoRetorno = CONST.INDEFINIDO;
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        Simbolo metodoLlamado = Utilidades.existeMetodo(coleccion.getMetodosPy(), llamada);
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

    public void analizarConstructorJava(Simbolo simbolo, LlamadaJava llamada, Coleccion coleccion) {
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        
        Clase estructura = (Clase) coleccion.getClasesJv().getSimbolo(simbolo.getTipo()).getValor();
        Simbolo metodoLlamado = Utilidades.existeMetodo(estructura.getMetodos(), simbolo.getTipo(), llamada);
        if(metodoLlamado==null){
            coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un constructor con los parametros enviados", llamada.getPosicion());
        }else{
            simbolo.setValor(new Object());
        }
    }

    private void analizarLlamadaMetodoPythonSinReturn(LlamadaPython llamada, Coleccion coleccion) {
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        Simbolo metodoLlamado = Utilidades.existeMetodo(coleccion.getMetodosPy(), llamada);
        if(metodoLlamado==null){
            coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
        }
    }

    private void analizarLlamadaMetodoJavaSinReturn(LlamadaJava llamada, Coleccion coleccion) {
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(llamada.getIdVariable());
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        if(simbolo!=null){
            if(simbolo.getValor()==null){
                coleccion.getErrores().agregarError("Semantico", llamada.getIdVariable(), "No se ha instanciado el objeto para el cual se realizo una llamada de un metodo", llamada.getPosicion());
            }else{
                Clase estructura = (Clase) coleccion.getClasesJv().getSimbolo(simbolo.getTipo()).getValor();
                Simbolo metodoLlamado = Utilidades.existeMetodo(estructura.getMetodos(), simbolo.getTipo(), llamada);
                if(metodoLlamado==null){
                    coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
                }
            }
        }
    }

    private void analizarLlamadaMetodoVisualSinReturn(LlamadaVisual llamada, Coleccion coleccion) {
        for (NodoAritmetico parametro : llamada.getParametros()) parametro.analizarSemanticamente(coleccion);
        Simbolo metodoLlamado = Utilidades.existeMetodo(coleccion.getMetodosVb(), llamada);
        if(metodoLlamado==null){
            coleccion.getErrores().agregarError("Semantico",llamada.getIdMetodo(),"No existe un metodo con el identificador utilizado o los parametros enviados", llamada.getPosicion());
        }
    }
    
}
