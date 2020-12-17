/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.accionesAsignacion.AccionExpresion;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoBooleano;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion de una instruccion For
 * @author jose_
 */
public class ForInstr implements Instruccion{
    
    private DeclaracionInstr variableFor;
    private AsignacionInstr valorInicial;
    private NodoBooleano condicion;
    private Instruccion sentenciaFinal;
    private Pos posicion;
    private List<Instruccion> instrucciones;

    public ForInstr() {
    }

    public ForInstr(DeclaracionInstr variableFor, AsignacionInstr valorInicial, NodoBooleano condicion, Instruccion sentenciaFinal, Pos posicion, List<Instruccion> instrucciones) {
        this.variableFor = variableFor;
        this.valorInicial = valorInicial;
        this.condicion = condicion;
        this.sentenciaFinal = sentenciaFinal;
        this.posicion = posicion;
        this.instrucciones = instrucciones;
    }

    public DeclaracionInstr getVariableFor() {
        return variableFor;
    }

    public void setVariableFor(DeclaracionInstr variableFor) {
        this.variableFor = variableFor;
    }

    public AsignacionInstr getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(AsignacionInstr valorInicial) {
        this.valorInicial = valorInicial;
    }

    public NodoBooleano getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoBooleano condicion) {
        this.condicion = condicion;
    }

    public Instruccion getSentenciaFinal() {
        return sentenciaFinal;
    }

    public void setSentenciaFinal(Instruccion sentenciaFinal) {
        this.sentenciaFinal = sentenciaFinal;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        coleccion.getSimbolos().agregarAmbitoTemporal();
        if(variableFor!=null){ //si hay una instruccion de declaracion
            if(variableFor.getTipo()==null){ //si no se especifico un tipo
               if(!coleccion.getSimbolos().existeSimbolo(variableFor.getLugar().getId())) { //y la variable no existe
                   coleccion.getErrores().agregarError("Semantico",variableFor.getLugar().getId(),"La variable utilizada para el ciclo no fue declarada antes.",variableFor.getPosicion());
               }
            }else{
                variableFor.analizarSemanticamente(coleccion);
            }
        }
        valorInicial.analizarSemanticamente(coleccion);
        if(!coleccion.getSimbolos().getSimbolo(valorInicial.getLugar().getId()).getTipo().equals(CONST.ENTERO)) coleccion.getErrores().agregarError("Semantico",variableFor.getLugar().getId(),"La variable utilizada para el ciclo no es entera.",variableFor.getPosicion());
        condicion.analizarSemanticamente(coleccion);
        if(!(sentenciaFinal instanceof AsignacionInstr)){
            coleccion.getErrores().agregarError("Semantico",variableFor.getLugar().getId(),"Al final del ciclo no se modifica la variable utilizada.",sentenciaFinal.getPosicion());
        }else{
            AsignacionInstr sentencia = (AsignacionInstr) sentenciaFinal;
            if(variableFor.getLugar().getId().equals(sentencia.getLugar().getId())) coleccion.getErrores().agregarError("Semantico", sentencia.getLugar().getId(), "En el modificador del ciclo no se utiliza la misma variable que se declaro al inicio.", sentencia.getPosicion());
            ((AccionExpresion)sentencia.getAccion()).getExpresion().analizarSemanticamente(coleccion);
        }
        coleccion.getSimbolos().eliminarAmbitoTemporal();
    }
    
}
