/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos;

/**
 * Clase multiproposito destinada a almacenar informacion usando su tipo, para discernir de su utilidad
 * @author jose_
 */
public class Dato {
    
    private String tipo;
    private Object valor;

    public Dato() {
    }

    public Dato(String tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
}
