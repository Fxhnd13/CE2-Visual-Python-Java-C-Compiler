/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.analisis;

/**
 * Clase destinada al almacenamiento de los datos de un error encontrado
 * @author jose_
 */
public class ErrorA {
    
    private String tipo, cadena, descripcion;
    private Pos posicion;
    
    /**
     * Constructor de la clase
     * @param tipo tipo del error 
     * @param cadena cadena con la que se genero el error
     * @param descripcion descripcion del mismo
     * @param posicion posicion dentro del documento en la que se encuentra
     */
    public ErrorA(String tipo, String cadena, String descripcion, Pos posicion) {
        this.tipo = tipo;
        this.cadena = cadena;
        this.descripcion = descripcion;
        this.posicion = posicion;
    }

    public ErrorA() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
}
