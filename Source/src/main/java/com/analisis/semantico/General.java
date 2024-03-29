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
import com.analisis.objetos.nodos.Hoja;
import com.analisis.sintactico.GeneradorAst;
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
    
    public void analizar(GeneradorAst ast) {
        ColeccionInstr instrucciones = ast.getInstrucciones();
        EstructurasIntermedias generadorStr = new EstructurasIntermedias(); //generamos las estructuras intermedias necesarias
        coleccion = generadorStr.generarEstructuras(instrucciones, ast.getErrores());
        
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
        coleccion.setSimbolos(new TablaDeSimbolos());
        AnalizadorBloque analizador = new AnalizadorBloque();
        TablaDeSimbolos metodosVb = new TablaDeSimbolos(), metodosPy = new TablaDeSimbolos(), clasesJv = new TablaDeSimbolos();
        for (Instruccion instruccion : instruccionesPr) {
            if(instruccion instanceof LibreriaInstr){
                LibreriaInstr instr = (LibreriaInstr) instruccion;
                switch(instr.getTipo()){
                    case CONST.SEC_VB:{
                        metodosVb = coleccion.getMetodosVb();
                        break;
                    }
                    case CONST.SEC_JV:{
                        if(instr.getDatos().get(0).equals("*")){
                            clasesJv = coleccion.getClasesJv();
                        }else{
                            Simbolo simbolo = coleccion.getClasesJv().getSimbolo(instr.getDatos().get(0));
                            if(simbolo!=null){
                                if(!clasesJv.agregarSimboloSiNoExiste(simbolo)){
                                    coleccion.getErrores().agregarError("Semantico", instr.getDatos().get(0), "Ya se importo una vez la clase especificada.", instr.getPosicion());
                                }
                            }else{
                                coleccion.getErrores().agregarError("Semantico", instr.getDatos().get(0), "No existe una clase java declara con el identificador especificado.", instr.getPosicion());
                            }
                        }
                        break;
                    }
                    case CONST.SEC_PY:{
                        metodosPy = coleccion.getMetodosPy();
                        break;
                    }
                }
            }
        }
        coleccion.setMetodosPy(metodosPy);
        coleccion.setMetodosVb(metodosVb);
        coleccion.setClasesJv(clasesJv);
        analizador.analizarBloque(instruccionesPr, coleccion);
    }

    private void analizarSeccionVisual(List<Instruccion> instruccionesVb) {
        for (Instruccion instruccion : instruccionesVb) {
            if(instruccion instanceof MetodoInstr){
                analizarInstruccionDeclaracionMetodo((MetodoInstr)instruccion, CONST.SEC_VB);
            }
        }
    }

    private void analizarSeccionJava(List<Instruccion> instruccionesJv) {
        for (Instruccion instruccion : instruccionesJv) {
            if(instruccion instanceof ClaseInstr){
                coleccion.setClase(((ClaseInstr)instruccion).getId());
                for (Instruccion instruccionDeClase : ((ClaseInstr)instruccion).getInstrucciones()) {
                    if(instruccionDeClase instanceof MetodoInstr){
                        analizarInstruccionDeclaracionMetodo((MetodoInstr)instruccionDeClase, CONST.SEC_JV);
                    }else if(instruccionDeClase instanceof AsignacionInstr){
                        ((AsignacionInstr)instruccionDeClase).analizarSemanticamente(coleccion);
                    }
                }
            }
        }
        coleccion.setClase(null);
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
        analizarParametrosDelMetodo(instr, seccion); //verificamos que no haya inconsistencias en la declaracion de los parametros
        
        if(!instr.getTipoRetorno().equals(CONST.CONSTRUCTOR))
            analizarRetornoDelMetodo(instr, seccion); //verificamos que esté bien el return (si es que se debe analizar)
        
        coleccion.setSimbolos(new TablaDeSimbolos());
        analizarParametrosDelMetodo(instr, seccion);
        
        AnalizadorBloque analizador = new AnalizadorBloque();
        analizador.analizarBloque(instr.getInstrucciones(), coleccion);
    }
    
    private void analizarRetornoDelMetodo(MetodoInstr instr, String seccion){
        if(!seccion.equals(CONST.SEC_PY)){
            AnalizadorReturn verificarReturn = new AnalizadorReturn();
            if(!instr.getTipoRetorno().equals(CONST.VOID)){
                if(!verificarReturn.tieneReturn(instr.getInstrucciones(), coleccion, instr.getTipoRetorno())){
                    //si el tipo es distinto a void y no tiene return, error
                    if(seccion.equals(CONST.SEC_JV)){
                        coleccion.getErrores().agregarError("Semantico",instr.getId(),"El metodo no posee un valor de retorno.",instr.getPosicion());
                    }else{
                        Simbolo simbolo = new Simbolo(instr.getId(),CONST.VAR,instr.getTipoRetorno(),null,null,null,null);
                        verificarReturn.tieneReturn(instr.getInstrucciones(), coleccion, seccion, simbolo);
                        if(simbolo.getValor()==null){
                            coleccion.getErrores().agregarError("Semantico",instr.getId(),"El metodo no posee un valor de retorno.",instr.getPosicion());
                        }else{
                            instr.getInstrucciones().add(new ReturnInstr(new Hoja(new Dato(CONST.ID,simbolo.getId()),null),null));
                        }
                    }
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
        if(seccion.equals(CONST.SEC_VB)) coleccion.getSimbolos().agregarSimbolo(new Simbolo(instr.getId(),CONST.VAR,instr.getTipoRetorno(),"1",null,instr.getId(),null));
        for (Dato parametro : instr.getParametros()) {
            if(!coleccion.getSimbolos().agregarSimboloSiNoExiste(new Simbolo((String)parametro.getValor(),CONST.VAR,parametro.getTipo(),"1",null,instr.getId(),null))){
                coleccion.getErrores().agregarError("Semantico", (String)parametro.getValor(), "Ya existe un parametro declarado con el identificador utilizado", parametro.getPosicion());
            }
        }
    }
    
}
