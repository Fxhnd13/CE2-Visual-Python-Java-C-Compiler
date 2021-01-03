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
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de un nodo modulo o residuo y el manejo del mismo
 * @author jose_
 */
public class Mod implements NodoAritmetico{

    private NodoAritmetico izquierdo, derecho;
    private String tipoRetorno;
    private Pos posicion;

    public Mod() {
    }

    public Mod(NodoAritmetico izquierdo, NodoAritmetico derecho, Pos posicion) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.posicion = posicion;
    }
    
    @Override
    public Dato analizarSemanticamente(Coleccion coleccion) {
        Dato izquierdo = this.izquierdo.analizarSemanticamente(coleccion);
        Dato derecho = this.derecho.analizarSemanticamente(coleccion);
        TablaDeTipos tablaDeTipos = new TablaDeTipos(this.posicion);
        return tablaDeTipos.verificarTiposDeOperacionAritmetica(2, izquierdo, derecho, coleccion);
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        
        List<Cuarteto> cuartetosI = new ArrayList();
        List<Cuarteto> cuartetosD = new ArrayList();
        
        cuartetosI = izquierdo.generarCuartetos(coleccion);
        String temporalI = (coleccion.getUltimoReturn()==null)?Temporal.actualTemporal():coleccion.getUltimoReturn();
        coleccion.setUltimoReturn(null);
        
        cuartetosD = derecho.generarCuartetos(coleccion);
        String temporalD = (coleccion.getUltimoReturn()==null)?Temporal.actualTemporal():coleccion.getUltimoReturn();
        coleccion.setUltimoReturn(null);
        
        String temporalS = Temporal.siguienteTemporal(this.tipoRetorno);
        
        if(!cuartetosI.isEmpty())Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosI);
        if(!cuartetosD.isEmpty())Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosD);
        cuartetosRetorno.add(new Cuarteto("%",temporalI, temporalD, temporalS));
        
        return cuartetosRetorno;
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

    @Override
    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public Pos getPosicion() {
        return this.posicion;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }
    
}
