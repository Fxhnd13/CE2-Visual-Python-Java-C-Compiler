/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.IngresoInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion de ingreso de datos en python
 * @author jose_
 */
public class IngresoInstrPy implements InstruccionPy{
    
    private Identado identado;
    private IngresoInstr instruccion;

    public IngresoInstrPy() {
    }

    public IngresoInstrPy(Identado identado, IngresoInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public IngresoInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(IngresoInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
