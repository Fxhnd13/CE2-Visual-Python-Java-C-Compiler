/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.TablaDeTipos;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de un nodo Mas y el manejo del mismo
 * @author jose_
 */
public class Mas implements NodoAritmetico {
    
    private NodoAritmetico izquierdo, derecho;
    private String tipoRetorno;
    private Pos posicion;

    public Mas() {
    }

    public Mas(NodoAritmetico izquierdo, NodoAritmetico derecho, Pos posicion) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.posicion = posicion;
    }

    @Override
    public Dato analizarSemanticamente(Coleccion coleccion) {
        Dato izquierdo = this.izquierdo.analizarSemanticamente(coleccion);
        Dato derecho = this.derecho.analizarSemanticamente(coleccion);
        TablaDeTipos tablaDeTipos = new TablaDeTipos(this.posicion);
        return tablaDeTipos.verificarTiposDeOperacionAritmetica(3, izquierdo, derecho, coleccion);
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public NodoAritmetico getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAritmetico izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAritmetico getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAritmetico derecho) {
        this.derecho = derecho;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    @Override
    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public Pos getPosicion() {
        return this.posicion;
    }
    
}
