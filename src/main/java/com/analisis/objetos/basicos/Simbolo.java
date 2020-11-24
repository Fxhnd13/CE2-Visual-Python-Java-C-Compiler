/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos;

/**
 * Clase destinada al almacenamiento de la informacion de un simbolo
 * @author jose_
 */
public class Simbolo {
    
    private String id,tipo,size,direccion,ambito;
    private Object valor;

    public Simbolo() {
    }

    public Simbolo(String id, String tipo, String size, String direccion, String ambito, Object valor) {
        this.id = id;
        this.tipo = tipo;
        this.size = size;
        this.direccion = direccion;
        this.ambito = ambito;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
}
