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
import com.analisis.objetos.nodos.Concat;
import com.analisis.objetos.nodos.Scanf;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al almacenamiento de un instruccion del tipo ingreso de datos
 * @author jose_
 */
public class IngresoInstr implements Instruccion{
    
    private AccionIngreso accion;
    private Pos posicion;

    public IngresoInstr() {
    }

    public IngresoInstr(AccionIngreso accion, Pos posicion) {
        this.accion = accion;
        this.posicion = posicion;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(AccionIngreso accion) {
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
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(accion.getMensaje()!=null){
            Cuartetos.unirCuartetos(cuartetosRetorno, accion.getMensaje().generarCuartetos(coleccion));
        }
        cuartetosRetorno.add(new Cuarteto("read",null,null,null));
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        if(accion.getMensaje()!=null){
            int erroresAntes = coleccion.getErrores().getErrores().size();
            accion.getMensaje().analizarSemanticamente(coleccion);
            if(erroresAntes == coleccion.getErrores().getErrores().size()){
                if(accion.getMensaje() instanceof Scanf){
                    accion.setMensaje(new Concat((Scanf) accion.getMensaje()));
                }
            }
        }
    }
    
}
