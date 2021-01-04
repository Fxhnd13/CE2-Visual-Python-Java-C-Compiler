/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.ErrorManager;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.ClaseInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.DeclaracionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.MetodoInstr;
import com.generadores.objetos.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class EstructurasIntermedias {
    
    public Coleccion generarEstructuras(ColeccionInstr instrucciones, ErrorManager errores) {
        Coleccion coleccion = new Coleccion();
        coleccion.setErrores(errores);
        
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
                
                //agregamos los simbolos de la clase y si tienen valores iniciales (asignaciones)
                for (Instruccion instruccionDeClaseActual : ((ClaseInstr)instruccion).getInstrucciones()) {
                    
                    if(instruccionDeClaseActual instanceof DeclaracionInstr){
                        //agregamos una variable
                        if(!clase.getSimbolos().agregarSimboloSiNoExiste(simboloDeVariable((DeclaracionInstr)instruccionDeClaseActual, String.valueOf(clase.getSimbolos().getSimbolos().size())))){
                            coleccion.getErrores().agregarError("Semantico",((DeclaracionInstr)instruccion).getLugar().getId(), "Ya existe un atributo declarado para la clase con el identificador utilizado", instruccion.getPosicion());
                        }
                        
                    }else if(instruccionDeClaseActual instanceof AsignacionInstr){
                        clase.getAsignaciones().add((AsignacionInstr)instruccionDeClaseActual);
                    }
                    
                }
                
                //agregamos los metodos
                for(Instruccion instruccionDeClaseActual : ((ClaseInstr)instruccion).getInstrucciones()){
                    if(instruccionDeClaseActual instanceof MetodoInstr){
                        MetodoInstr metodo = (MetodoInstr) instruccionDeClaseActual;
                        //agregamos el metodo
                        if(metodo.getTipoRetorno().equals(CONST.CONSTRUCTOR)){
                            if(!metodo.getId().equals(((ClaseInstr)instruccion).getId())){
                                coleccion.getErrores().agregarError("Semantico", metodo.getId(), "El constructor debe tener el mismo id que la clase en la que se declara.", metodo.getPosicion());
                            }else{
                                cambiarInstruccionesDeConstructor(metodo, clase.getAsignaciones());
                            }
                        }
                        Simbolo simboloDeMetodo = simboloDeMetodo(metodo, CONST.SEC_JV);
                        if(Utilidades.existeMetodo(clase.getMetodos(), simboloDeMetodo)){
                            coleccion.getErrores().agregarError("Semantico", ((MetodoInstr) instruccion).getId(), "Ya existe un metodo con el identificador utilizado y los mismos parametros", instruccion.getPosicion());
                        }else{
                            clase.getMetodos().agregarSimbolo(simboloDeMetodo);
                        }
                    }
                }
                
                MetodoInstr nuevaInstr = new MetodoInstr(CONST.CONSTRUCTOR,((ClaseInstr)instruccion).getId(),new ArrayList(),new ArrayList(),instruccion.getPosicion());
                cambiarInstruccionesDeConstructor(nuevaInstr, clase.getAsignaciones());
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

    private Simbolo simboloDeVariable(DeclaracionInstr instr, String direccion) {
        return new Simbolo(instr.getLugar().getId(),CONST.VAR, instr.getTipo(),"1",direccion,null,null);
    }

    private void cambiarInstruccionesDeConstructor(MetodoInstr metodo, List<Instruccion> asignaciones) {
        List<Instruccion> instruccionesConstructor = new ArrayList();
        for (Instruccion asignacion : asignaciones) {
            instruccionesConstructor.add(asignacion);
        }
        for (Instruccion instrTemporal : metodo.getInstrucciones()) {
            instruccionesConstructor.add(instrTemporal);
        }
        metodo.setInstrucciones(instruccionesConstructor);
    }
    
}
