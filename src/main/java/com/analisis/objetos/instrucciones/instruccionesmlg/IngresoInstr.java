/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.accionesAsignacion.Accion;

/**
 * Clase destinada al almacenamiento de un instruccion del tipo ingreso de datos
 * @author jose_
 */
public class IngresoInstr implements Instruccion{
    
    private Accion accion;
    private Pos posicion;

    public IngresoInstr() {
    }

    public IngresoInstr(Accion accion, Pos posicion) {
        this.accion = accion;
        this.posicion = posicion;
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
