/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analisis.objetos.basicos.lugaresAsignacion;

import com.analisis.objetos.analisis.Pos;

/**
 *
 * @author Jose Soberanis
 */
public class LugarVariableGlobal implements Lugar{

    private String id;
    private Pos posicion;

    public LugarVariableGlobal() {
    }

    public LugarVariableGlobal(String id, Pos posicion) {
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
