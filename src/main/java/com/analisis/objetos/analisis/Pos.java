/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.analisis;

/**
 * Clase destinada al almacenamiento de los datos de la posicion, x (columna) y y(fila/linea)
 * @author jose_
 */
public class Pos {
    
    private int y,x;

    public Pos() {
        y=-1;
        x=-1;
    }

    /**
     * @param y linea
     * @param x columna
     */
    public Pos(int y, int x) {
        this.y = y;
        this.x = x;
    }

    /**
     * @return la linea
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return la columna
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return ((y==(-1))&&(x==(-1)))? "Posicion desconocida" : "(Linea: " + y + ", Columna: " + x + ')';
    }
    
}
