/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.accionesAsignacion;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.nodos.Mensaje;
import com.analisis.objetos.nodos.Mensaje;

/**
 * Clase destinada a almacenar la informacion cuando se quiere asignar a una variable un valor ingresado por el usaurio
 * @author jose_
 */
public class AccionIngreso implements Accion{
    
    private Mensaje mensaje;
    private String tipoRetorno;
    private Pos posicion;

    public AccionIngreso() {
    }

    public AccionIngreso(Mensaje mensaje, String tipoRetorno, Pos posicion) {
        this.mensaje = mensaje;
        this.tipoRetorno = tipoRetorno;
        this.posicion = posicion;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
