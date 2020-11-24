/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

import com.analisis.objetos.instrucciones.instruccionesmlg.ForInstr;

/**
 * Clase destinada a almacenar la informacion de una instruccion for en python
 * @author jose_
 */
public class ForInstrPy {
    
    private Identado identado;
    private ForInstr instruccion;

    public ForInstrPy() {
    }

    public ForInstrPy(Identado identado, ForInstr instruccion) {
        this.identado = identado;
        this.instruccion = instruccion;
    }

    public Identado getIdentado() {
        return identado;
    }

    public void setIdentado(Identado identado) {
        this.identado = identado;
    }

    public ForInstr getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(ForInstr instruccion) {
        this.instruccion = instruccion;
    }
    
}
