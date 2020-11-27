/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;

/**
 *
 * @author jose_
 */
public interface NodoAritmetico {
    
    public Dato analizarSemanticamente(Coleccion coleccion);
}
