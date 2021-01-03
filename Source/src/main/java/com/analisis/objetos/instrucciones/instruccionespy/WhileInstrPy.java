/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.WhileInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion While de py
 * @author jose_
 */
public class WhileInstrPy implements InstruccionPy{
    
    private Identado identado;
    private WhileInstr instruccion;

    public WhileInstrPy() {
    }

    public WhileInstrPy(Identado identado, WhileInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public WhileInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(WhileInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
