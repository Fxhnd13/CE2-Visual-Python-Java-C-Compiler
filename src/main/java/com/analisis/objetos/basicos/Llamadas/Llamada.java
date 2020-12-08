/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.Llamadas;

import com.analisis.objetos.nodos.NodoAritmetico;
import java.util.List;

/**
 * Interface destinada para las distintas llamadas en los metodos
 * @author jose_
 */
public interface Llamada {
    
    public String getIdMetodo();
    public List<NodoAritmetico> getParametros();
}
