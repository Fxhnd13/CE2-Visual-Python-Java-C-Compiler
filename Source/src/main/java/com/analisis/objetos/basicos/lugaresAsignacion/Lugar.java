/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.basicos.lugaresAsignacion;

import com.analisis.objetos.analisis.Pos;

/**
 * Interfaz destinada a implementarse en los lugares en los que se puede realizar una asignacion
 * @author jose_
 */
public interface Lugar {
    
    public String getId();
    public Pos getPosicion();
}
