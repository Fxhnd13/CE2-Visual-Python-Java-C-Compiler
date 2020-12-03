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
public class And implements NodoBooleano{

    private NodoBooleano izquierdo, derecho;
    private String etiquetaSi, etiquetaNo;
    private Pos posicion;

    public And() {
    }

    public And(NodoBooleano izquierdo, NodoBooleano derecho, Pos posicion) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.posicion = posicion;
    }
    
    @Override
    public Dato analizarSemanticamente(Coleccion coleccion) {
        Dato izquierdo = this.izquierdo.analizarSemanticamente(coleccion);
        Dato derecho = this.derecho.analizarSemanticamente(coleccion);
        boolean derechoOperable = false, izquierdoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.BOOLEAN)){
                izquierdoOperable = true;
            }else{
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador AND no valido para un operando tipo "+izquierdo.getTipo(), izquierdo.getPosicion());
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.BOOLEAN)){
                derechoOperable = true;
            }else{
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador AND no valido para un operando tipo "+derecho.getTipo(), derecho.getPosicion());
            }
        }
        if(izquierdoOperable && derechoOperable){
            return new Dato(CONST.BOOLEAN, null);
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
