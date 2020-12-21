/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.semantico.AnalizadorLlamadaMetodo;
import com.generadores.Codigo3Direcciones;
import com.generadores.objetos.Cuarteto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class LlamadaInstr implements Instruccion{
    
    private Llamada llamada;
    private Pos posicion;

    public LlamadaInstr() {
    }

    public LlamadaInstr(Llamada llamada, Pos posicion) {
        this.llamada = llamada;
        this.posicion = posicion;
    }

    public Llamada getLlamada() {
        return llamada;
    }

    public void setLlamada(Llamada llamada) {
        this.llamada = llamada;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        
        Codigo3Direcciones generador = new Codigo3Direcciones();
        List<Cuarteto> cuartetosRetorno = generador.generarCuartetos(llamada, coleccion, false);
        
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
        analizador.analizarLLamada(llamada, coleccion);
    }
    
}
