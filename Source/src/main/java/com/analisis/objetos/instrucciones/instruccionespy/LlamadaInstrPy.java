/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.LlamadaInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion de llamada a metodo en python
 * @author jose_
 */
public class LlamadaInstrPy implements InstruccionPy{
    
    private Identado identado;
    private LlamadaInstr instruccion;

    public LlamadaInstrPy() {
    }

    public LlamadaInstrPy(Identado identado, LlamadaInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public LlamadaInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(LlamadaInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
