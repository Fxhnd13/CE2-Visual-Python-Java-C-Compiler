/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;

/**
 * Clase destinada a almacenar la informacion de una instruccion de return
 * @author jose_
 */
public class ReturnInstr {
    
    private NodoAritmetico expresion;
    private Pos posicion;

    public ReturnInstr() {
    }

    public ReturnInstr(NodoAritmetico expresion, Pos posicion) {
        this.expresion = expresion;
        this.posicion = posicion;
    }

    public NodoAritmetico getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoAritmetico expresion) {
        this.expresion = expresion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
