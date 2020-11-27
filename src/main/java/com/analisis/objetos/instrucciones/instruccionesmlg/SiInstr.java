/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoBooleano;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion Si... o Sino Si...
 * @author jose_
 */
public class SiInstr implements InstruccionSino{
    
    private NodoBooleano condicion;
    private List<Instruccion> instrucciones;
    private InstruccionSino instruccionSino;
    private Pos posicion;

    public SiInstr() {
    }

    public SiInstr(NodoBooleano condicion, List<Instruccion> instruccionesSi, InstruccionSino instruccionSino, Pos posicion) {
        this.condicion = condicion;
        this.instrucciones = instruccionesSi;
        this.instruccionSino = instruccionSino;
        this.posicion = posicion;
    }

    public NodoBooleano getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoBooleano condicion) {
        this.condicion = condicion;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instruccionesSi) {
        this.instrucciones = instruccionesSi;
    }

    public InstruccionSino getInstruccionSino() {
        return instruccionSino;
    }

    public void setInstruccionSino(InstruccionSino instruccionSino) {
        this.instruccionSino = instruccionSino;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}