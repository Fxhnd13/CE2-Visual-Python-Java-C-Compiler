/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;

/**
 * Clase destinada al almacenamiento de la informacion de una instruccion de declaracion
 * @author jose_
 */
public class DeclaracionInstr implements Instruccion{
    
    private String tipo;
    private Lugar lugar;
    private Pos posicion;
    private boolean constante;

    public DeclaracionInstr() {
    }

    public DeclaracionInstr(String tipo, Lugar lugar, Pos posicion, boolean constante) {
        this.tipo = tipo;
        this.lugar = lugar;
        this.posicion = posicion;
        this.constante = constante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public boolean isConstante() {
        return constante;
    }

    public void setConstante(boolean constante) {
        this.constante = constante;
    }
    
}
