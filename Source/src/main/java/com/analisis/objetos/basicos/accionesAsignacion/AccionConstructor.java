/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.accionesAsignacion;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Llamadas.LlamadaJava;

/**
 * Clase destinada a almacenar la informacion del constructor llamado
 * @author jose_
 */
public class AccionConstructor implements Accion{
    
    private LlamadaJava constructor;
    private Pos posicion;

    public AccionConstructor() {
    }

    public AccionConstructor(LlamadaJava constructor, Pos posicion) {
        this.constructor = constructor;
        this.posicion = posicion;
    }

    public LlamadaJava getConstructor() {
        return constructor;
    }

    public void setConstructor(LlamadaJava constructor) {
        this.constructor = constructor;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
