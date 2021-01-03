/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import com.analisis.objetos.estructuras.Metodo;
import com.analisis.objetos.estructuras.TablaDeSimbolos;
import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.ClaseInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.DeclaracionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.MetodoInstr;
import com.generadores.objetos.Utilidades;
import java.util.ArrayList;

/**
 *
 * @author jose_
 */
public class EstructurasIntermedias {
    
    public Coleccion generarEstructuras(ColeccionInstr instrucciones) {
        Coleccion coleccion = new Coleccion();
        
        //metodos de visual declarados
        for (Instruccion instruccion : instrucciones.getInstruccionesVb()) {
            if(instruccion instanceof MetodoInstr){
                //agregamos el metodo
                Simbolo simboloDeMetodo = simboloDeMetodo((MetodoInstr)instruccion, CONST.SEC_VB);
                if(Utilidades.existeMetodo(coleccion.getMetodosVb(), simboloDeMetodo)){
                    coleccion.getErrores().agregarError("Semantico", ((MetodoInstr) instruccion).getId(), "Ya existe un metodo con el identificador utilizado y los mismos parametros", instruccion.getPosicion());
                }else{
                    coleccion.getMetodosVb().agregarSimbolo(simboloDeMetodo);
                }
            }
        }
        
        //clases de java declaradas junto con el listado de sus metodos
        for (Instruccion instruccion : instrucciones.getInstruccionesJv()) {
            if(instruccion instanceof ClaseInstr){
                Clase clase = new Clase();
                for (Instruccion instruccionDeClaseActual : ((ClaseInstr)instruccion).getInstrucciones()) {
                    if(instruccionDeClaseActual instanceof MetodoInstr){
                        //agregamos el metodo
                        Simbolo simboloDeMetodo = simboloDeMetodo((MetodoInstr)instruccionDeClaseActual, CONST.SEC_JV);
                        if(Utilidades.existeMetodo(clase.getMetodos(), simboloDeMetodo)){
                            coleccion.getErrores().agregarError("Semantico", ((MetodoInstr) instruccion).getId(), "Ya existe un metodo con el identificador utilizado y los mismos parametros", instruccion.getPosicion());
                        }else{
                            clase.getMetodos().agregarSimbolo(simboloDeMetodo);
                        }
                    }else if(instruccionDeClaseActual instanceof DeclaracionInstr){
                        //agregamos una variable
                        if(!clase.getMetodos().agregarSimboloSiNoExiste(simboloDeVariable((DeclaracionInstr)instruccionDeClaseActual))){
                            coleccion.getErrores().agregarError("Semantico",((DeclaracionInstr)instruccion).getLugar().getId(), "Ya existe un atributo declarado para la clase con el identificador utilizado", instruccion.getPosicion());
                        }
                    }else if(instruccionDeClaseActual instanceof AsignacionInstr){
                        clase.getAsignaciones().add((AsignacionInstr)instruccionDeClaseActual);
                    }
                }
                MetodoInstr nuevaInstr = new MetodoInstr(CONST.VOID,((ClaseInstr)instruccion).getId(),new ArrayList(),new ArrayList(),instruccion.getPosicion());
                if(clase.getMetodos().agregarSimboloSiNoExiste(simboloDeMetodo(nuevaInstr,CONST.SEC_JV))){
                    ((ClaseInstr)instruccion).getInstrucciones().add(nuevaInstr);
                }
                coleccion.getClasesJv().agregarSimbolo(simboloDeClase((ClaseInstr)instruccion, clase));
            }
        }
        
        //metodos de python declarados
        for (Instruccion instruccion : instrucciones.getInstruccionesPy()) {
            if(instruccion instanceof MetodoInstr){
                //agregamos el metodo
                Simbolo simboloDeMetodo = simboloDeMetodo((MetodoInstr)instruccion, CONST.SEC_PY);
                if(Utilidades.existeMetodo(coleccion.getMetodosPy(), simboloDeMetodo)){
                    coleccion.getErrores().agregarError("Semantico", ((MetodoInstr) instruccion).getId(), "Ya existe un metodo con el identificador utilizado y los mismos parametros", instruccion.getPosicion());
                }else{
                    coleccion.getMetodosPy().agregarSimbolo(simboloDeMetodo);
                }
            }
        }
            
        return coleccion;
    }

    private Simbolo simboloDeMetodo(MetodoInstr instr, String seccion) {        
        return new Simbolo(instr.getId(),CONST.METODO, instr.getTipoRetorno(),null,null,seccion,instr);
    }

    private Simbolo simboloDeClase(ClaseInstr instr, Clase clase) {
        return new Simbolo(instr.getId(),CONST.CLASE, instr.getId(),null,null,CONST.SEC_JV,clase);
    }

    private Simbolo simboloDeVariable(DeclaracionInstr instr) {
        return new Simbolo(instr.getLugar().getId(),CONST.VAR, instr.getTipo(),"1",null,null,null);
    }
    
}
