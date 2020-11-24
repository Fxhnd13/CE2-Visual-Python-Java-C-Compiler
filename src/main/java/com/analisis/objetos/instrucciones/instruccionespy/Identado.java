/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionespy;

/**
 * Clase destinada a almacenar la informacion del identado que poseera una instruccion de python
 * @author jose_
 */
public class Identado {
    
    private int espacios,tabulaciones;

    public Identado() {
    }

    public Identado(int espacios, int tabulaciones) {
        this.espacios = espacios;
        this.tabulaciones = tabulaciones;
    }

    public int getEspacios() {
        return espacios;
    }

    public void setEspacios(int espacios) {
        this.espacios = espacios;
    }

    public int getTabulaciones() {
        return tabulaciones;
    }

    public void setTabulaciones(int tabulaciones) {
        this.tabulaciones = tabulaciones;
    }
    
    public int getTotal(){
        return espacios+tabulaciones;
    }

    public boolean equals(Identado comparador) {
        return (espacios==comparador.getEspacios()&&tabulaciones==comparador.getTabulaciones());
    }
    
}
