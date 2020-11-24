/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

/**
 * Clase destinada a almacenar la informacion de las variables temporales utilizadas a lo largo de la generacion
 * de codigo intermedio (3 direccion y posiblemente assembler)
 * @author jose_
 */
public class VarT {
    
    private String tipo, temporal;

    public VarT() {
    }

    public VarT(String tipo, String temporal) {
        this.tipo = tipo;
        this.temporal = temporal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
        this.temporal = temporal;
    }
    
}
