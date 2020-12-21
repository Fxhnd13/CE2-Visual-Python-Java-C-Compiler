/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class Concat {
    
    private List<NodoAritmetico> mensajes;
    private Pos posicion;

    public Concat() {
        this(new ArrayList(),new Pos());
    }

    public Concat(List<NodoAritmetico> mensajes, Pos posicion) {
        this.mensajes = mensajes;
        this.posicion = posicion;
    }

    public List<NodoAritmetico> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<NodoAritmetico> mensajes) {
        this.mensajes = mensajes;
    }
    
    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public void analizarSemanticamente(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
