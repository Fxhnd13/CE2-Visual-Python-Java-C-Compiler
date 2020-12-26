/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fxhnd
 */
public class Scanf implements Mensaje {

    private List<Dato> mensajes;
    private List<NodoAritmetico> expresiones;
    private Pos posicion;

    public Scanf() {
    }

    public Scanf(List<Dato> mensajes, List<NodoAritmetico> expresiones, Pos posicion) {
        this.mensajes = mensajes;
        this.expresiones = expresiones;
        this.posicion = posicion;
    }

    public List<Dato> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Dato> mensajes) {
        this.mensajes = mensajes;
    }

    public List<NodoAritmetico> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(List<NodoAritmetico> expresiones) {
        this.expresiones = expresiones;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    public void analizarSemanticamente(Coleccion coleccion) {
        List<Dato> comodines = new ArrayList();
        for (Dato mensaje : mensajes) {
            if(mensaje.getTipo().equals(CONST.COMODIN_CARACTER)||mensaje.getTipo().equals(CONST.COMODIN_ENTERO)||mensaje.getTipo().equals(CONST.COMODIN_FLOTANTE)) comodines.add(mensaje);
        }
        if(comodines.size()!=expresiones.size()){
            coleccion.getErrores().agregarError("Semantico", "Sin cadena|", "La cantidad de comodines y expresiones a mostrar no coinciden (comodines: "+comodines+", expresiones: "+expresiones.size(), posicion);
        }else{
            for (int i = 0; i < comodines.size(); i++) {
                Dato dato = expresiones.get(i).analizarSemanticamente(coleccion);
                if(dato!=null){
                    if(dato.getTipo().equals(comodines.get(i)))coleccion.getErrores().agregarError("Semantico","Sin cadena", "El tipo de la expresion No. "+(i+1)+" no coincide con el comodin No."+(i+1), posicion);
                }
            }
        }
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
