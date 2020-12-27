/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 *
 * @author jose_
 */
public class Not implements NodoBooleano{

    private NodoBooleano hijo;
    private String etiquetaSi, etiquetaNo;
    private Pos posicion;

    public Not() {
    }

    public Not(NodoBooleano hijo, Pos posicion) {
        this.hijo = hijo;
        this.posicion = posicion;
    }
    
    @Override
    public Dato analizarSemanticamente(Coleccion coleccion) {
        Dato hijo = this.hijo.analizarSemanticamente(coleccion);
        if(hijo != null){
            if(!hijo.getTipo().equals(CONST.BOOLEAN)){
                coleccion.getErrores().agregarError("Semantico",(String) hijo.getValor(),"El operador 'not' no es aplicable a un valor/variable tipo "+hijo.getTipo(),hijo.getPosicion());
            }else{
                return new Dato(CONST.BOOLEAN,null);
            }
        }
        return null;
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = hijo.generarCuartetos(coleccion);
        this.etiquetaNo = hijo.getEtiquetaSi();
        this.etiquetaSi = hijo.getEtiquetaNo();
        return cuartetosRetorno;
    }

    @Override
    public String getEtiquetaSi() {
        return etiquetaSi;
    }

    @Override
    public String getEtiquetaNo() {
        return etiquetaNo;
    }

    @Override
    public Pos getPosicion() {
        return posicion;
    }

    @Override
    public void setEtiquetaSi(String etiquetaSi) {
        this.etiquetaSi = etiquetaSi;
    }

    @Override
    public void setEtiquetaNo(String etiquetaNo) {
        this.etiquetaNo = etiquetaNo;
    }

    @Override
    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    public NodoBooleano getHijo(){
        return this.hijo;
    }

    public void setHijo(NodoBooleano hijo){
        this.hijo = hijo;
    }
}
