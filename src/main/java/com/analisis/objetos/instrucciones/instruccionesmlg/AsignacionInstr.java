/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.accionesAsignacion.Accion;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;

/**
 * Clase destinada a almacenar la informacion de una instruccion de asignacion
 * @author jose_
 */
public class AsignacionInstr implements Instruccion{
    
    private Lugar lugar;
    private Accion accion;
    private Pos posicion;

    public AsignacionInstr() {
    }

    public AsignacionInstr(Lugar lugar, Accion accion, Pos posicion) {
        this.lugar = lugar;
        this.accion = accion;
        this.posicion = posicion;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
