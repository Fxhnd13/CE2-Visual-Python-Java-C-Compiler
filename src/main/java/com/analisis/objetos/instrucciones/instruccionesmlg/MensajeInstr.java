/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class MensajeInstr implements Instruccion{
    
    private NodoAritmetico mensaje;
    private Pos posicion;

    public MensajeInstr() {
    }

    public MensajeInstr(NodoAritmetico mensaje, Pos posicion) {
        this.mensaje = mensaje;
        this.posicion = posicion;
    }

    public NodoAritmetico getMensaje() {
        return mensaje;
    }

    public void setMensaje(NodoAritmetico mensaje) {
        this.mensaje = mensaje;
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
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(mensaje!=null){
            Cuartetos.unirCuartetos(cuartetosRetorno, mensaje.generarCuartetos(coleccion));
        }
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        mensaje.analizarSemanticamente(coleccion);
    }
    
}
