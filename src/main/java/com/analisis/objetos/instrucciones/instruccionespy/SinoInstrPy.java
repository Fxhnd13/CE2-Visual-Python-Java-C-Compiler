/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.SinoInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion sino de python
 * @author jose_
 */
public class SinoInstrPy implements InstruccionSinoPy{
    
    private Identado identado;
    private SinoInstr instruccion;

    public SinoInstrPy() {
    }

    public SinoInstrPy(Identado identado, SinoInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public SinoInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(SinoInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
