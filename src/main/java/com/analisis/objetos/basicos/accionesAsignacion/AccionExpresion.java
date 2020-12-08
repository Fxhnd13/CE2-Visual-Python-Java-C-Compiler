/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.accionesAsignacion;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;

/**
 * Clase destinada a almacenar la informacion cuando se quiere asignar un valor de una expresion
 * @author jose_
 */
public class AccionExpresion implements Accion{
    
    private NodoAritmetico expresion;
    private Pos posicion;

    public AccionExpresion() {
    }

    public AccionExpresion(NodoAritmetico expresion, Pos posicion) {
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
