/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al manejo y almacenamiento de las instrucciones generadas por el paser y lexer
 * @author jose_
 */
public class ColeccionInstr {
    
    private List<Instruccion> instruccionesVb, instruccionesJv, instruccionesPy, instruccionesPr;
    
    public ColeccionInstr() {
        instruccionesVb = new ArrayList();
        instruccionesJv = new ArrayList();
        instruccionesPy = new ArrayList();
        instruccionesPr = new ArrayList();
    }

    public List<Instruccion> getInstruccionesVb() {
        return instruccionesVb;
    }

    public void setInstruccionesVb(List<Instruccion> instruccionesVb) {
        this.instruccionesVb = instruccionesVb;
    }

    public List<Instruccion> getInstruccionesJv() {
        return instruccionesJv;
    }

    public void setInstruccionesJv(List<Instruccion> instruccionesJv) {
        this.instruccionesJv = instruccionesJv;
    }

    public List<Instruccion> getInstruccionesPy() {
        return instruccionesPy;
    }

    public void setInstruccionesPy(List<Instruccion> instruccionesPy) {
        this.instruccionesPy = instruccionesPy;
    }

    public List<Instruccion> getInstruccionesPr() {
        return instruccionesPr;
    }

    public void setInstruccionesPr(List<Instruccion> instruccionesPr) {
        this.instruccionesPr = instruccionesPr;
    }
    
}
