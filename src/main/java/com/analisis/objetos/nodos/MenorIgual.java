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
import java.util.List;

/**
 *
 * @author jose_
 */
public class MenorIgual implements NodoBooleano{

    private NodoAritmetico izquierdo, derecho;
    private String etiquetaSi, etiquetaNo;
    private Pos posicion;

    public MenorIgual() {
    }

    public MenorIgual(NodoAritmetico izquierdo, NodoAritmetico derecho, Pos posicion) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.posicion = posicion;
    }
    
    @Override
    public Dato analizarSemanticamente(Coleccion coleccion) {
        Dato izquierdo = this.izquierdo.analizarSemanticamente(coleccion);
        Dato derecho = this.derecho.analizarSemanticamente(coleccion);
        boolean izquierdoOperable = false, derechoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico","Sin especificar","No es posible usar el operador 'distinto de' con un valor tipo Objeto.", izquierdo.getPosicion());
            }else if(izquierdo.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico","Sin especificar","No es posible usar el operador 'distinto de' con un valor tipo Void.", izquierdo.getPosicion());
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico","Sin especificar","No es posible usar el operador 'distinto de' con un valor tipo Objeto.", derecho.getPosicion());
            }else if(derecho.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico","Sin especificar","No es posible usar el operador 'distinto de' con un valor tipo Void. ", derecho.getPosicion());
            }
        }
        if(izquierdoOperable && derechoOperable){
            if(izquierdo.getTipo().equals(derecho.getTipo())){
                return new Dato(CONST.BOOLEAN, null);
            }else{
                if(izquierdo.getTipo().equals(CONST.CARACTER) || derecho.getTipo().equals(CONST.CARACTER)){
                    coleccion.getErrores().agregarError("Semantico","Sin especificar","No es posible comparar un valor tipo Char con otro distinto. ("+izquierdo.getTipo()+","+derecho.getTipo()+")", this.getPosicion());
                }else{
                    return new Dato(CONST.BOOLEAN, null);
                }
            }
        }
        return null;
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEtiquetaSi() {
        return etiquetaSi;
    }

    @Override
    public String getEtiquetaNo() {
        return etiquetaNo;
    }

    @Override
    public Pos getPosicion() {
        return posicion;
    }

    @Override
    public void setEtiquetaSi(String etiquetaSi) {
        this.etiquetaSi = etiquetaSi;
    }

    @Override
    public void setEtiquetaNo(String etiquetaNo) {
        this.etiquetaNo = etiquetaNo;
    }

    @Override
    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
}
