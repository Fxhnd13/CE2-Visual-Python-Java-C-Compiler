/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.estructuras.Coleccion;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 *
 * @author jose_
 */
public interface Instruccion {
    
    public void generarCodigoAssembler(Coleccion coleccion);
    public List<Cuarteto> generarCuartetos(Coleccion coleccion);
    public void analizarSemanticamente(Coleccion coleccion);
    public Pos getPosicion();
}
