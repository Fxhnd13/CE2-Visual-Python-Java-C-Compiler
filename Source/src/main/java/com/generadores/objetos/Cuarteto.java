/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

/**
 * Clase destinada a almacenar la informacion de un cuarteto
 * @author jose_
 */
public class Cuarteto {
    
    private String op,iz,der,res;

    public Cuarteto() {
    }

    public Cuarteto(String op, String iz, String der, String res) {
        this.op = op;
        this.iz = iz;
        this.der = der;
        this.res = res;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getIz() {
        return iz;
    }

    public void setIz(String iz) {
        this.iz = iz;
    }

    public String getDer() {
        return der;
    }

    public void setDer(String der) {
        this.der = der;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
    
}
