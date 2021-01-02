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
 * Interface destinada al manejo de cualquier tipo de instruccion con sus metodos correspondientes
 * @author jose_
 */
public interface Instruccion {
    
    /**
    * Metodo para generar el codigo assembler *depreced*
    */
    public void generarCodigoAssembler(Coleccion coleccion);

    /**
    * Metodo para generar los cuartetos generados por la instruccion
    * @param colecion, tiene los datos necesarios para la generacion de codigo, las estructuras e instrucciones
    */
    public List<Cuarteto> generarCuartetos(Coleccion coleccion);

    /**
    * Metodo para analizar si las instrucciones tienen sentido semantico
    */
    public void analizarSemanticamente(Coleccion coleccion);

    /**
    * Metedo para obtener la posicion en la que se encuentra la instruccion
    */
    public Pos getPosicion();
    public void setPosicion(Pos posicion);
}
