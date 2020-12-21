/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Simbolo;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al manejo de los simbolos para agregar, modificar, eliminar y acceder
 * @author jose_
 */
public class TablaDeSimbolos {

    private List<Simbolo> simbolos;
    private List<Integer> ambitos, ambitosMetodo;
    
    public TablaDeSimbolos(){
        simbolos = new ArrayList();
        ambitos = new ArrayList();
        ambitosMetodo = new ArrayList();
        ambitos.add(0);
        ambitosMetodo.add(0);
    }

    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(List<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }
    
    public void agregarAmbitoTemporal(){
        ambitos.add(simbolos.size()-ambitos.get(ambitos.size()-1));
    }
    
    public void eliminarAmbitoTemporal(){
        int variablesAntes = 0;
        for (Integer ambito : ambitos) {
            variablesAntes+=ambito;
        }
        for (int i = simbolos.size()-1; i >= variablesAntes; i--) {
            simbolos.remove(simbolos.size()-1);
        }
        ambitos.remove(ambitos.size()-1);
    }
    
    public Simbolo getSimbolo(String nombre){
        for (Simbolo simbolo : simbolos) {
            if(simbolo.getId().equals(nombre)) return simbolo;
        }
        return null;
    }
    
    public boolean existeSimbolo(String nombre){
        for (Simbolo simbolo : simbolos) {
            if(simbolo.getId().equals(nombre)) return true;
        }
        return false;
    }
    
    public void agregarSimbolo(Simbolo simbolo){
        simbolos.add(simbolo);
        ordenarSimbolos();
    }
    
    public boolean agregarSimboloSiNoExiste(Simbolo simbolo){
        if(!existeSimbolo(simbolo.getId())){
            simbolos.add(simbolo);
            return true;
        }
        return false;
    }
    
    public String getUltimaPosicionLibre(List<Cuarteto> cuartetos){
        String pos = null;
        ordenarSimbolos();
        for(int i = simbolos.size()-1; i >= 0; i--){
            if(simbolos.get(i).getDireccion()!=null){
                String t = simbolos.get(i).getSize();
                String d = simbolos.get(i).getDireccion();
                try{
                    pos = String.valueOf(Integer.parseInt(t)+Integer.parseInt(d));
                    break;
                }catch(Exception ex){
                    cuartetos.add(new Cuarteto("+",t,d,Temporal.siguienteTemporal(CONST.ENTERO)));
                    pos = Temporal.actualTemporal();
                    break;
                }
            }
        }
        if(pos == null) pos = "0";
        return pos;
    }
    
    public String getVariablesDeclaradasEnElAmbitoActual(){
        return String.valueOf(ambitos.get(ambitos.size()-1));
    }

    private void ordenarSimbolos() {
        for (int i = simbolos.size()-1; i >= 0 ; i++) {
            if(simbolos.get(i).getDireccion()==null){
                Simbolo temporal = simbolos.get(i);
                simbolos.remove(i);
                simbolos.add(temporal);
            }
        }
    }
}
