/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.MensajeInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion mensaje de python
 * @author jose_
 */
public class MensajeInstrPy implements InstruccionPy{
    
    private Identado identado;
    private MensajeInstr instruccion;

    public MensajeInstrPy() {
    }

    public MensajeInstrPy(Identado identado, MensajeInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public MensajeInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(MensajeInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
