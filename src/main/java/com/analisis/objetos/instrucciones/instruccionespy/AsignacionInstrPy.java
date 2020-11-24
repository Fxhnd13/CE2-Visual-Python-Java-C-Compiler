/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;

/**
 * Clase destinada a almacenar la informacion
 * @author jose_
 */
public class AsignacionInstrPy implements InstruccionPy{
    
    private Identado identado;
    private AsignacionInstr instruccion;

    public AsignacionInstrPy() {
    }

    public AsignacionInstrPy(Identado identado, AsignacionInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public AsignacionInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(AsignacionInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
