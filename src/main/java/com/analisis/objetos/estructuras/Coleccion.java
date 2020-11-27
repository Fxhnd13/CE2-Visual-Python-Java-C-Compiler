/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.analisis.ErrorA;
import com.analisis.objetos.basicos.Simbolo;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de las diferentes secciones
 * @author jose_
 */
public class Coleccion {
    
    private int tipadoActual;
    private String clase = null;
    private TablaDeSimbolos metodosPy, metodosVb, clasesJv, simbolos;
    private List<String> nombresMetodos;
    private List<ErrorA> errores;

    public Coleccion() {
    }

    public Coleccion(int tipado, TablaDeSimbolos simbolos, TablaDeSimbolos metodosPy, TablaDeSimbolos metodosVb, TablaDeSimbolos clasesJv, List<String> nombresMetodos, List<ErrorA> errores) {
        this.metodosPy = metodosPy;
        this.metodosVb = metodosVb;
        this.clasesJv = clasesJv;
        this.nombresMetodos = nombresMetodos;
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

    public List<String> getNombresMetodos() {
        return nombresMetodos;
    }

    public void setNombresMetodos(List<String> nombresMetodos) {
        this.nombresMetodos = nombresMetodos;
    }

    public List<ErrorA> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorA> errores) {
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
}
