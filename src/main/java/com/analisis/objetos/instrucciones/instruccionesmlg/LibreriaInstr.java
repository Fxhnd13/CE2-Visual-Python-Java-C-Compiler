/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de una libreria para el codigo C
 * @author jose_
 */
public class LibreriaInstr implements Instruccion{
    
    private List<String> datos;
    private Pos posicion;

    public LibreriaInstr() {
    }

    public LibreriaInstr(List<String> datos, Pos posicion) {
        this.datos = datos;
        this.posicion = posicion;
    }

    public List<String> getDatos() {
        return datos;
    }

    public void setDatos(List<String> datos) {
        this.datos = datos;
    }
    
    public Pos getPosicion() {
        return this.posicion;
    }
    
}
