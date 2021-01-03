/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.SiInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion Sino de python
 * @author jose_
 */
public class SinoSiInstrPy implements InstruccionSinoPy{
    
    private Identado identado;
    private SiInstr instruccion;

    public SinoSiInstrPy() {
    }

    public SinoSiInstrPy(Identado identado, SiInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public SiInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(SiInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
