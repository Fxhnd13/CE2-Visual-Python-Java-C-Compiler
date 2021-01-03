/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion de return
 * @author jose_
 */
public class ReturnInstr implements Instruccion{
    
    private NodoAritmetico expresion;
    private Pos posicion;

    public ReturnInstr() {
    }

    public ReturnInstr(NodoAritmetico expresion, Pos posicion) {
        this.expresion = expresion;
        this.posicion = posicion;
    }

    public NodoAritmetico getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoAritmetico expresion) {
        this.expresion = expresion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        Cuartetos.unirCuartetos(cuartetosRetorno, expresion.generarCuartetos(coleccion));
        String temporalRetorno = Temporal.actualTemporal();
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,"0",Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto(":=a",temporalRetorno,Temporal.actualTemporal(),CONST.STACK));
        cuartetosRetorno.add(new Cuarteto("goto",null,null,coleccion.getEtiquetaReturn()));
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void analizarSemanticamente(Coleccion coleccion, String tipo){
        Dato dato = this.expresion.analizarSemanticamente(coleccion);
        if(dato!=null){
            if(!tipo.equals(dato.getTipo())){
                coleccion.getErrores().agregarError("Semantico", "return", "El tipo de dato a retornar no coincide con el declarado en el método", posicion);
            }
        }
    }
    
}
