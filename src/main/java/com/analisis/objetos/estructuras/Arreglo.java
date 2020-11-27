/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de un arreglo al ser declarado, mas no utilizado posteriormente
 * @author jose_
 */
public class Arreglo {
    
    private List<NodoAritmetico> dimensiones;
    private List<String> temporales;
    private Pos posicion;

    public Arreglo() {
    }

    public Arreglo(List<NodoAritmetico> dimensiones, List<String> temporales, Pos posicion) {
        this.dimensiones = dimensiones;
        this.temporales = temporales;
        this.posicion = posicion;
    }

    public List<String> getTemporales() {
        return temporales;
    }

    public void setTemporales(List<String> temporales) {
        this.temporales = temporales;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public List<NodoAritmetico> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(List<NodoAritmetico> dimensiones) {
        this.dimensiones = dimensiones;
    }
    
}
