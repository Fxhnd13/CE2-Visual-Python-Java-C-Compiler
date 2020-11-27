/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion switch
 * @author jose_
 */
public class SwitchInstr {
     
    private Lugar lugarAsignacion;
    private List<CaseInstr> casos;
    private CaseInstr porDefecto;
    private Pos posicion;

    public SwitchInstr() {
    }

    public SwitchInstr(Lugar lugarAsignacion, List<CaseInstr> casos, CaseInstr porDefecto, Pos posicion) {
        this.lugarAsignacion = lugarAsignacion;
        this.casos = casos;
        this.porDefecto = porDefecto;
        this.posicion = posicion;
    }

    public Lugar getLugarAsignacion() {
        return lugarAsignacion;
    }

    public void setLugarAsignacion(Lugar lugarAsignacion) {
        this.lugarAsignacion = lugarAsignacion;
    }

    public List<CaseInstr> getCasos() {
        return casos;
    }

    public void setCasos(List<CaseInstr> casos) {
        this.casos = casos;
    }

    public CaseInstr getPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(CaseInstr porDefecto) {
        this.porDefecto = porDefecto;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}