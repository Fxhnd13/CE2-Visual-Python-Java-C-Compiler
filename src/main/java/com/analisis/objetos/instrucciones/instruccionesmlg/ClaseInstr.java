/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la una instruccion que declara una clase
 * @author jose_
 */
public class ClaseInstr implements Instruccion{
    
    private String id;
    private List<Instruccion> instrucciones;
    private Pos posicion;

    public ClaseInstr() {
    }

    public ClaseInstr(String id, List<Instruccion> instrucciones, Pos posicion) {
        this.id = id;
        this.instrucciones = instrucciones;
        this.posicion = posicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
