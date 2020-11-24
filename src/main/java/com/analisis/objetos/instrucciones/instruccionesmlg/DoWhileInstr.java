/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion para una instruccion do...while
 * @author jose_
 */
public class DoWhileInstr implements Instruccion{
    
    private NodoAritmetico condiciones;
    private List<Instruccion> instrucciones;
    private Pos posicion;

    public DoWhileInstr() {
    }

    public DoWhileInstr(NodoAritmetico condiciones, List<Instruccion> instrucciones, Pos posicion) {
        this.condiciones = condiciones;
        this.instrucciones = instrucciones;
        this.posicion = posicion;
    }

    public NodoAritmetico getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(NodoAritmetico condiciones) {
        this.condiciones = condiciones;
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
