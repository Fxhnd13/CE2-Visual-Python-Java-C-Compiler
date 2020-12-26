/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.lugaresAsignacion;

import com.analisis.objetos.analisis.Pos;

/**
 * Clase destinada a la asignacion de una clase, exclusivamente al llamar un constructor o igualar a otra clase
 * @author jose_
 */
public class LugarClase implements Lugar{
    
    private String id;
    private Pos posicion;

    public LugarClase() {
    }

    public LugarClase(String id, Pos posicion) {
        this.id = id;
        this.posicion = posicion;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
