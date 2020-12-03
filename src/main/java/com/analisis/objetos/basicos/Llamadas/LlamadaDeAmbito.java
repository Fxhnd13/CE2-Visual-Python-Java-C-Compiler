/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.Llamadas;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.NodoAritmetico;
import java.util.List;

/**
 *
 * @author jose_
 */
public class LlamadaDeAmbito {
    
    private String idMetodo;
    private List<NodoAritmetico> parametros;
    private Pos posicion;

    public LlamadaDeAmbito() {
    }

    public LlamadaDeAmbito(String idMetodo, List<NodoAritmetico> parametros, Pos posicion) {
        this.idMetodo = idMetodo;
        this.parametros = parametros;
        this.posicion = posicion;
    }

    public String getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(String idMetodo) {
        this.idMetodo = idMetodo;
    }

    public List<NodoAritmetico> getParametros() {
        return parametros;
    }

    public void setParametros(List<NodoAritmetico> parametros) {
        this.parametros = parametros;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
