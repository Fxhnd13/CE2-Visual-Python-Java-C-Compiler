/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.MetodoInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion que declara un metodo en python
 * @author jose_
 */
public class MetodoInstrPy implements InstruccionPy{
    
    private Identado identado;
    private MetodoInstr instruccion;

    public MetodoInstrPy() {
    }

    public MetodoInstrPy(Identado identado, MetodoInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public MetodoInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(MetodoInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
