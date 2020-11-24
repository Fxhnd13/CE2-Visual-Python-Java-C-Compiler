/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;

/**
 * Clase destinada al almacenamiento de la informacion de un break
 * @author jose_
 */
public class BreakInstr implements Instruccion{
    
    private Pos posicion;

    public BreakInstr() {
    }

    public BreakInstr(Pos posicion) {
        this.posicion = posicion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
