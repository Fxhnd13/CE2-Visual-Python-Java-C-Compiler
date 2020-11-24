/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.ReturnInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion Return de python
 * @author jose_
 */
public class ReturnInstrPy implements InstruccionPy{
    
    private Identado identado;
    private ReturnInstr instruccion;

    public ReturnInstrPy() {
    }

    public ReturnInstrPy(Identado identado, ReturnInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public ReturnInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(ReturnInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
