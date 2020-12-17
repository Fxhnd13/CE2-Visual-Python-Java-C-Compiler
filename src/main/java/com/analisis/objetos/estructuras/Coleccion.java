/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.analisis.ErrorA;
import com.analisis.objetos.analisis.ErrorManager;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Simbolo;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de las diferentes secciones
 * @author jose_
 */
public class Coleccion {
    
    private int tipadoActual, caso;
    private String clase = null, ultimoReturn = null;
    private TablaDeSimbolos metodosPy, metodosVb, clasesJv, simbolos;
    private ErrorManager errores;

    public Coleccion() {
        metodosPy = new TablaDeSimbolos();
        metodosVb = new TablaDeSimbolos();
        clasesJv = new TablaDeSimbolos();
        simbolos = new TablaDeSimbolos();
        errores = new ErrorManager();
    }

    public Coleccion(int tipado, TablaDeSimbolos simbolos, TablaDeSimbolos metodosPy, TablaDeSimbolos metodosVb, TablaDeSimbolos clasesJv, ErrorManager errores) {
        this.metodosPy = metodosPy;
        this.metodosVb = metodosVb;
        this.clasesJv = clasesJv;
        this.errores = errores;
    }

    public TablaDeSimbolos getMetodosPy() {
        return metodosPy;
    }

    public void setMetodosPy(TablaDeSimbolos metodosPy) {
        this.metodosPy = metodosPy;
    }

    public TablaDeSimbolos getMetodosVb() {
        return metodosVb;
    }

    public void setMetodosVb(TablaDeSimbolos metodosVb) {
        this.metodosVb = metodosVb;
    }

    public TablaDeSimbolos getClasesJv() {
        return clasesJv;
    }

    public void setClasesJv(TablaDeSimbolos clasesJv) {
        this.clasesJv = clasesJv;
    }
    
    public ErrorManager getErrores() {
        return errores;
    }

    public void setErrores(ErrorManager errores) {
        this.errores = errores;
    }

    public int getTipadoActual() {
        return tipadoActual;
    }

    public void setTipadoActual(int tipadoActual) {
        this.tipadoActual = tipadoActual;
    }

    public TablaDeSimbolos getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(TablaDeSimbolos simbolos) {
        this.simbolos = simbolos;
    }
    
    public void setClase(String clase){
        this.clase = clase;
    }
    
    public String getClase(){
        return this.clase;
    }

    public Simbolo getAtributoDeClase(String nombre) {
        return ((Clase)clasesJv.getSimbolo(this.clase).getValor()).getSimbolos().getSimbolo(nombre);
    }

    public boolean existeMetodo(Llamada llamada) {
        System.out.println("Sin implementación la busqueda de un metodo");
        return false;
    }
    
    public boolean isEnCaso(){ 
        return this.caso > 0;
    }
    
    public int getCaso(){
        return this.caso;
    }
    
    public void setCaso(int caso){
        this.caso = caso;
    }

    public String getUltimoReturn() {
        return ultimoReturn;
    }

    public void setUltimoReturn(String ultimoReturn) {
        this.ultimoReturn = ultimoReturn;
    }
    
}
