/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import java.util.List;

/**
 * Clase destinada al almacenamiento de un caso de un switch
 * @author jose_
 */
public class CaseInstr {
    
    private Dato valor;
    private List<Instruccion> instrucciones;
    private Pos posicion;

    public CaseInstr() {
    }

    public CaseInstr(Dato valor, List<Instruccion> instrucciones, Pos posicion) {
        this.valor = valor;
        this.instrucciones = instrucciones;
        this.posicion = posicion;
    }

    public Dato getValor() {
        return valor;
    }

    public void setValor(Dato valor) {
        this.valor = valor;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
