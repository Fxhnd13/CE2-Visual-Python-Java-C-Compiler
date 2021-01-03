/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 *
 * @author jose_
 */
public interface NodoBooleano {
    
    public Dato analizarSemanticamente(Coleccion coleccion);
    public List<Cuarteto> generarCuartetos(Coleccion coleccion);
    
    public String getEtiquetaSi();
    public String getEtiquetaNo();
    public Pos getPosicion();
    
    public void setEtiquetaSi(String etiquetaSi);
    public void setEtiquetaNo(String etiquetaNo);
    public void setPosicion(Pos posicion);
}
