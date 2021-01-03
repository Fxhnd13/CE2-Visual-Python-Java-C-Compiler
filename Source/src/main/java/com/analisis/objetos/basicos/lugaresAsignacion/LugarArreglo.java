/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.lugaresAsignacion;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion cuando se desea asignar en un arreglo o cuando
 * se desea acceder a una posicion del mismo
 * @author jose_
 */
public class LugarArreglo implements Lugar{
    
    private String id;
    private List<NodoAritmetico> indices;
    private Pos posicion;

    public LugarArreglo() {
    }

    public LugarArreglo(String id, List<NodoAritmetico> indices, Pos posicion) {
        this.id = id;
        this.indices = indices;
        this.posicion = posicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<NodoAritmetico> getIndices() {
        return indices;
    }

    public void setIndices(List<NodoAritmetico> indices) {
        this.indices = indices;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
