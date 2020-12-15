/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.semantico;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import com.analisis.objetos.estructuras.TablaDeSimbolos;
import com.analisis.objetos.instrucciones.instruccionesmlg.*;
import com.generadores.EstructurasIntermedias;
import java.util.List;

/**
 *
 * @author jose_
 */
public class General {

    private Coleccion coleccion; 
    
    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }
    
    public void analizar(ColeccionInstr instrucciones) {
        EstructurasIntermedias generadorStr = new EstructurasIntermedias(); //generamos las estructuras intermedias necesarias
        coleccion = generadorStr.generarEstructuras(instrucciones);
        
        coleccion.setTipadoActual(0);//tipado de visual
        analizarSeccionVisual(instrucciones.getInstruccionesVb());
        
        coleccion.setTipadoActual(1);//tipado de java
        analizarSeccionJava(instrucciones.getInstruccionesJv());
        
        coleccion.setTipadoActual(2);//tipado de python
        analizarSeccionPython(instrucciones.getInstruccionesPy());
        
        coleccion.setTipadoActual(3);//tipado de c
        analizarSeccionPrincipal(instrucciones.getInstruccionesPr());
    }

    private void analizarSeccionPrincipal(List<Instruccion> instruccionesPr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analizarSeccionVisual(List<Instruccion> instruccionesVb) {
        for (Instruccion instruccion : instruccionesVb) {
            if(instruccion instanceof MetodoInstr){
                analizarInstruccionDeclaracionMetodo((MetodoInstr)instruccion, CONST.SEC_VB);
            }
        }
    }

    private void analizarSeccionJava(List<Instruccion> instruccionesJv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analizarSeccionPython(List<Instruccion> instruccionesPy) {
        for (Instruccion instruccion : instruccionesPy) {
            if(instruccion instanceof MetodoInstr){
                analizarInstruccionDeclaracionMetodo((MetodoInstr)instruccion, CONST.SEC_PY);
            }
        }
    }

    private void analizarInstruccionDeclaracionMetodo(MetodoInstr instr, String seccion) {
        coleccion.setSimbolos(new TablaDeSimbolos()); //la tabla de simbolos con la que se evaluara el metodo
        analizarRetornoDelMetodo(instr, seccion); //verificamos que esté bien el return (si es que se debe analizar)
        analizarParametrosDelMetodo(instr, seccion); //verificamos que no haya inconsistencias en la declaracion de los parametros
        analizarBloque(instr.getInstrucciones());
    }
    
    private void analizarRetornoDelMetodo(MetodoInstr instr, String seccion){
        if(!seccion.equals(CONST.SEC_PY)){
            AnalizadorReturn verificarReturn = new AnalizadorReturn();
            if(!instr.getTipoRetorno().equals(CONST.VOID)){
                if(!verificarReturn.tieneReturn(instr.getInstrucciones(), coleccion, instr.getTipoRetorno())){
                    //si el tipo es distinto a void y no tiene return, error
                    coleccion.getErrores().agregarError("Semantico",instr.getId(),"El metodo no posee un valor de retorno.",instr.getPosicion());
                }
            }else{
                if(verificarReturn.tieneReturn(instr.getInstrucciones(), coleccion, instr.getTipoRetorno())){
                    //si el tipo es igual a void y tiene return, error
                    coleccion.getErrores().agregarError("Semantico",instr.getId(),"El metodo es tipo void y posee un valor de retorno.",instr.getPosicion());
                }
            }
        }
    }

    private void analizarParametrosDelMetodo(MetodoInstr instr, String seccion) {
        for (Dato parametro : instr.getParametros()) {
            if(!coleccion.getSimbolos().agregarSimboloSiNoExiste(new Simbolo((String)parametro.getValor(),CONST.VAR,parametro.getTipo(),"1",null,instr.getId(),null))){
                coleccion.getErrores().agregarError("Semantico", (String)parametro.getValor(), "Ya existe un parametro declarado con el identificador utilizado", parametro.getPosicion());
            }
        }
    }

    public void analizarBloque(List<Instruccion> instrucciones) {
        for (int i = 0; i < instrucciones.size(); i++) {
            Instruccion instruccion = instrucciones.get(i);
            if(instruccion instanceof DeclaracionInstr){
                ((DeclaracionInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof AsignacionInstr){
                ((AsignacionInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof DoWhileInstr){
                ((DoWhileInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof WhileInstr){
                ((WhileInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof ForInstr){
                ((ForInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof SiInstr){
                ((SiInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof SwitchInstr){
                ((SwitchInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof IngresoInstr){
                ((IngresoInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof LlamadaInstr){
                ((LlamadaInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof MensajeInstr){
                ((MensajeInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof LimpiarInstr){
                //no se hace nada, literalmente se ignora
            }else if(instruccion instanceof ReturnInstr){
                ((ReturnInstr)instruccion).analizarSemanticamente(coleccion);
            }else if(instruccion instanceof BreakInstr){
                if(!coleccion.isEnCaso()){
                    //error no estamos en un caso de switch
                }else{
                    //verificamos que no haya más instrucciones dentro de esta seccion si las hay entonces hay error
                }
            }else if(instruccion instanceof LibreriaInstr){
                //se agrega para un posterior uso(?)
            }
        }
    }
}
