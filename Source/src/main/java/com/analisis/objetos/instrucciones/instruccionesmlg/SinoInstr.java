/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.semantico.AnalizadorBloque;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion sino
 * @author jose_
 */
public class SinoInstr implements InstruccionSino{
    
    private List<Instruccion> instrucciones;
    private Pos posicion;

    public SinoInstr() {
    }

    public SinoInstr(List<Instruccion> instrucciones, Pos posicion) {
        this.instrucciones = instrucciones;
        this.posicion = posicion;
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

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        AnalizadorBloque analizador = new AnalizadorBloque();
        coleccion.getSimbolos().agregarAmbitoTemporal();
        analizador.analizarBloque(instrucciones, coleccion);
        coleccion.getSimbolos().eliminarAmbitoTemporal();
    }
    
}
