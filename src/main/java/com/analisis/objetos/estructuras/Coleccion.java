/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

/**
 * Clase destinada a almacenar la informacion de las diferentes secciones
 * @author jose_
 */
public class Coleccion {
    
    private TablaDeSimbolos metodosPy, metodosVb, clasesJv;

    public Coleccion() {
    }

    public Coleccion(TablaDeSimbolos metodosPy, TablaDeSimbolos metodosVb, TablaDeSimbolos clasesJv) {
        this.metodosPy = metodosPy;
        this.metodosVb = metodosVb;
        this.clasesJv = clasesJv;
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
    
}
