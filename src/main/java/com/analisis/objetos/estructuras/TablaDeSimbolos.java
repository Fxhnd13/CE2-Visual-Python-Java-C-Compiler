/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.basicos.Simbolo;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al manejo de los simbolos para agregar, modificar, eliminar y acceder
 * @author jose_
 */
public class TablaDeSimbolos {

    private List<Simbolo> simbolos;
    
    public TablaDeSimbolos(){
        simbolos = new ArrayList();
    }
    
    public Simbolo getSimbolo(String nombre){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean existeSimbolo(String nombre){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void agregarSimbolo(Simbolo simbolo){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getUltimoPosicionLibre(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(List<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }
    
}
