/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.lugaresAsignacion.*;
import com.analisis.objetos.estructuras.Arreglo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de una instruccion de declaracion
 * @author jose_
 */
public class DeclaracionInstr implements Instruccion{
    
    private String tipo;
    private Lugar lugar;
    private Pos posicion;
    private boolean constante;

    public DeclaracionInstr() {
    }

    public DeclaracionInstr(String tipo, Lugar lugar, Pos posicion, boolean constante) {
        this.tipo = tipo;
        this.lugar = lugar;
        this.posicion = posicion;
        this.constante = constante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public boolean isConstante() {
        return constante;
    }

    public void setConstante(boolean constante) {
        this.constante = constante;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(lugar instanceof LugarVariable){
            
            String direccion = coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno);
            coleccion.getSimbolos().agregarSimbolo(new Simbolo(lugar.getId(),tipo,CONST.VAR,"1",direccion,null,null));
            
        }else if(lugar instanceof LugarArreglo){

            LugarArreglo lugarArreglo = (LugarArreglo) lugar;
            Arreglo arreglo = new Arreglo();
            
            for (NodoAritmetico expresion : lugarArreglo.getIndices()) {
                for (Cuarteto cuarteto : expresion.generarCuartetos(coleccion)) {
                    cuartetosRetorno.add(cuarteto);
                }
                arreglo.getTemporales().add(Temporal.actualTemporal());
            }
            String temporalSize = Temporal.siguienteTemporal(CONST.ENTERO);
            if(arreglo.getTemporales().size()==1){
                cuartetosRetorno.add(new Cuarteto(":=",arreglo.getTemporales().get(0),null,temporalSize));
            }else{
                cuartetosRetorno.add(new Cuarteto(":=","0",null,temporalSize));
                for (String temporal : arreglo.getTemporales()) {
                    cuartetosRetorno.add(new Cuarteto("*",temporal,temporalSize,temporalSize));
                }
            }
            String temporalDireccion = coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno);
            
            coleccion.getSimbolos().agregarSimbolo(new Simbolo(
                    lugar.getId(),
                    tipo,
                    CONST.ARREGLO,
                    temporalSize,
                    temporalDireccion,
                    null,
                    arreglo
            ));
            
            cuartetosRetorno.add(new Cuarteto("dclArreglo",null,temporalSize,lugar.getId()));

        }else if(lugar instanceof LugarClase){
            
            Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(tipo).getValor();
            
            coleccion.getSimbolos().agregarSimbolo(new Simbolo(
                    lugar.getId(),
                    CONST.CLASE,
                    tipo,
                    "1",
                    null,//direccion
                    null,
                    null
            ));
        }
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        Simbolo simboloParaAgregar = null;
        if(lugar instanceof LugarClase){
            //verificar que el tipo de instancia exista
            Simbolo simboloDeClase = coleccion.getClasesJv().getSimbolo(tipo);
            if(simboloDeClase!=null){
                simboloParaAgregar = new Simbolo(lugar.getId(), CONST.CLASE, tipo,null,null,null,null);
            }else{
                coleccion.getErrores().agregarError("Semantico", lugar.getId(), "No existe la clase utilizada en la definición de la seccion java.", lugar.getPosicion());
            }
        }else if (lugar instanceof LugarArreglo){
            //verificar que la declaracion se haya realizado correctamente
            LugarArreglo valor = (LugarArreglo) lugar;
            for (int i=0; i < valor.getIndices().size(); i++) {
                if(!valor.getIndices().get(i).analizarSemanticamente(coleccion).getTipo().equals(CONST.ENTERO)){
                    coleccion.getErrores().agregarError("Semantico","Dimension "+(i+1),"La dimension para la declaracion del arreglo no es un entero. ",valor.getIndices().get(i).getPosicion());
                }
            }
            simboloParaAgregar = new Simbolo(lugar.getId(),CONST.ARREGLO, tipo,null,null,null,new Arreglo(valor.getIndices(),new ArrayList(), lugar.getPosicion()));
        }else if (lugar instanceof LugarVariable){
            simboloParaAgregar = new Simbolo(lugar.getId(),CONST.VAR,tipo,null,null,null,null);
        }
        if(simboloParaAgregar!=null){
            if(!coleccion.getSimbolos().agregarSimboloSiNoExiste(simboloParaAgregar)){
                coleccion.getErrores().agregarError("Semantico", lugar.getId(), "Ya existe una variable declarada con el identificador utilizado.",lugar.getPosicion());
            }
        }
    }
    
}
