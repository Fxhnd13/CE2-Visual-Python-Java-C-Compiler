/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.analisis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class ErrorManager {
    
    private List<ErrorA> errores;
    
    public ErrorManager(){
        errores = new ArrayList();
    }
    
    /**
     *
     * @param tipo lexico/sintactico/semantico
     * @param cadena cadena que produjo el error
     * @param descripcion descripcion del problema, quiza una posible solucion
     * @param posicion posicion dentro del documento en el que se encontro el error
     */
    public void agregarError(String tipo, String cadena, String descripcion, Pos posicion){
        errores.add(new ErrorA(tipo,cadena,descripcion,posicion));
    }
    
    public List<ErrorA> getErrores(){
        return errores;
    }
}
