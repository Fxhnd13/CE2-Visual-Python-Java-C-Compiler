/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.accionesAsignacion.Llamada;

/**
 *
 * @author jose_
 */
public class LlamadaInstr implements Instruccion{
    
    private Llamada llamada;
    private Pos posicion;

    public LlamadaInstr() {
    }

    public LlamadaInstr(Llamada llamada, Pos posicion) {
        this.llamada = llamada;
        this.posicion = posicion;
    }

    public Llamada getLlamada() {
        return llamada;
    }

    public void setLlamada(Llamada llamada) {
        this.llamada = llamada;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
