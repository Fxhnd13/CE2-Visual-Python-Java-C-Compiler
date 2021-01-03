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

    private List<Dato> cadenas;
    private List<NodoAritmetico> mensajes;
    private Pos posicion;

    public Scanf() {
    }

    public Scanf(List<Dato> cadenas, List<NodoAritmetico> mensajes, Pos posicion) {
        this.cadenas = cadenas;
        this.mensajes = mensajes;
        this.posicion = posicion;
    }

    public List<Dato> getCadenas() {
        return cadenas;
    }

    public void setCadenas(List<Dato> cadenas) {
        this.cadenas = cadenas;
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
        List<Dato> comodines = new ArrayList();
        for (Dato mensaje : cadenas) {
            if(mensaje.getTipo().equals(CONST.COMODIN_CARACTER)||mensaje.getTipo().equals(CONST.COMODIN_ENTERO)||mensaje.getTipo().equals(CONST.COMODIN_FLOTANTE)) comodines.add(mensaje);
        }
        if(comodines.size()!=mensajes.size()){
            coleccion.getErrores().agregarError("Semantico", "Sin cadena|", "La cantidad de comodines y expresiones a mostrar no coinciden (comodines: "+comodines+", expresiones: "+mensajes.size(), posicion);
        }else{
            for (int i = 0; i < comodines.size(); i++) {
                Dato dato = mensajes.get(i).analizarSemanticamente(coleccion);
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
