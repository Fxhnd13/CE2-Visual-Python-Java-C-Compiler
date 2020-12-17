/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.accionesAsignacion.Accion;
import com.analisis.objetos.basicos.accionesAsignacion.AccionIngreso;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 * Clase destinada al almacenamiento de un instruccion del tipo ingreso de datos
 * @author jose_
 */
public class IngresoInstr implements Instruccion{
    
    private Accion accion;
    private Pos posicion;

    public IngresoInstr() {
    }

    public IngresoInstr(Accion accion, Pos posicion) {
        this.accion = accion;
        this.posicion = posicion;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        ((AccionIngreso)accion).getMensaje().analizarSemanticamente(coleccion);
    }
    
}
