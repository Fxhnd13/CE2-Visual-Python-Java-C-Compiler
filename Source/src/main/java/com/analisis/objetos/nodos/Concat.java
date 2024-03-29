/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class Concat implements Mensaje{
    
    private List<NodoAritmetico> mensajes;
    private Pos posicion;

    public Concat(Scanf nodo){
        mensajes = new ArrayList();
        this.posicion = nodo.getPosicion();
        int expresionesUsadas = 0;
        for (Dato mensaje : nodo.getCadenas()) {
            if(mensaje.getTipo().equals(CONST.COMODIN_CARACTER)||mensaje.getTipo().equals(CONST.COMODIN_ENTERO)||mensaje.getTipo().equals(CONST.COMODIN_FLOTANTE)){
                mensajes.add(nodo.getMensajes().get(expresionesUsadas++));
            }else{
                mensajes.add(new Hoja(mensaje, posicion));
            }
        }
    }
    
    public Concat(Pos pos){
        this(new ArrayList(), pos);
    }
    
    public Concat() {
        this(new ArrayList(), new Pos());
    }

    public Concat(List<NodoAritmetico> mensajes, Pos posicion) {
        this.mensajes = mensajes;
        this.posicion = posicion;
    }

    public List<NodoAritmetico> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<NodoAritmetico> mensajes) {
        this.mensajes = mensajes;
    }
    
    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    public void analizarSemanticamente(Coleccion coleccion) {
        for (NodoAritmetico mensaje : mensajes) {
            mensaje.analizarSemanticamente(coleccion);
        }
    }

    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        
        for (NodoAritmetico mensaje : mensajes) {
            if(mensaje instanceof Hoja){
                Hoja hoja = (Hoja)mensaje;
                if(hoja.getValor().getValor() instanceof Llamada){
                    Cuartetos.unirCuartetos(cuartetosRetorno, hoja.generarCuartetos(coleccion));
                    cuartetosRetorno.add(new Cuarteto("printDato",null,null,Temporal.actualTemporal()));
                    coleccion.setUltimoReturn(null);
                }else{
                    if(hoja.getValor().getTipo().equals(CONST.ARREGLO)){
                        Cuartetos.unirCuartetos(cuartetosRetorno, hoja.generarCuartetos(coleccion));
                        cuartetosRetorno.add(new Cuarteto("printDato",null,null,Temporal.actualTemporal()));
                    }else if(hoja.getValor().getTipo().equals(CONST.ID)){
                        Cuartetos.unirCuartetos(cuartetosRetorno, hoja.generarCuartetos(coleccion));
                        cuartetosRetorno.add(new Cuarteto("printDato",null,null,Temporal.actualTemporal()));
                    }else if(hoja.getValor().getTipo().equals(CONST.ID_GLOBAL)){
                        Cuartetos.unirCuartetos(cuartetosRetorno, hoja.generarCuartetos(coleccion));
                        cuartetosRetorno.add(new Cuarteto("printDato",null,null,Temporal.actualTemporal()));
                    }else{
                        cuartetosRetorno.add(new Cuarteto("printCadena",null,null,(String)hoja.getValor().getValor()));
                    }
                }
            }else{
                Cuartetos.unirCuartetos(cuartetosRetorno, mensaje.generarCuartetos(coleccion));
                cuartetosRetorno.add(new Cuarteto("printDato",null,null,Temporal.actualTemporal()));
            }
        }
        
        return cuartetosRetorno;
    }
    
}
