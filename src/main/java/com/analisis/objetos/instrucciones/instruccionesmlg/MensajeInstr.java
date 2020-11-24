/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;

/**
 *
 * @author jose_
 */
public class MensajeInstr implements Instruccion{
    
    private NodoAritmetico mensaje;
    private Pos posicion;

    public MensajeInstr() {
    }

    public MensajeInstr(NodoAritmetico mensaje, Pos posicion) {
        this.mensaje = mensaje;
        this.posicion = posicion;
    }

    public NodoAritmetico getMensaje() {
        return mensaje;
    }

    public void setMensaje(NodoAritmetico mensaje) {
        this.mensaje = mensaje;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
