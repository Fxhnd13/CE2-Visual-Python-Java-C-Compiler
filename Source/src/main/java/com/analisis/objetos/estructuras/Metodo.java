/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de un metodo (parametros necesarios e instrucciones que contiene)
 * @author jose_
 */
public class Metodo {
    
    private List<Dato> parametros;
    private List<Instruccion> instrucciones;

    public Metodo() {
    }

    public Metodo(List<Dato> parametros, List<Instruccion> instrucciones) {
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    public List<Dato> getParametros() {
        return parametros;
    }

    public void setParametros(List<Dato> parametros) {
        this.parametros = parametros;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }
    
}
