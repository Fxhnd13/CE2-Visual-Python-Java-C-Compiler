/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

/**
 * Clase destinada a almacenar la informacion de una clase
 * @author jose_
 */
public class Clase {
    
    private TablaDeSimbolos simbolos, metodos;

    public Clase() {
    }

    public Clase(TablaDeSimbolos simbolos, TablaDeSimbolos metodos) {
        this.simbolos = simbolos;
        this.metodos = metodos;
    }

    public TablaDeSimbolos getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(TablaDeSimbolos simbolos) {
        this.simbolos = simbolos;
    }

    public TablaDeSimbolos getMetodos() {
        return metodos;
    }

    public void setMetodos(TablaDeSimbolos metodos) {
        this.metodos = metodos;
    }
    
}
