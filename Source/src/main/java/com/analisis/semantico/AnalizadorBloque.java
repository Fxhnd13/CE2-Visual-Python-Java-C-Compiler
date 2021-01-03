/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analisis.semantico;

import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.BreakInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.DeclaracionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.DoWhileInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.ForInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.IngresoInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.LibreriaInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.LimpiarInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.LlamadaInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.MensajeInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.ReturnInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.SiInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.SwitchInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.WhileInstr;
import java.util.List;

/**
 *
 * @author Jose Soberanis
 */
public class AnalizadorBloque {

    public void analizarBloque(List<Instruccion> instrucciones, Coleccion coleccion) {
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
                //no se hace nada, tecnicamente ya se evaluo
                
            }else if(instruccion instanceof BreakInstr){
                if(!coleccion.isEnCaso()){
                    coleccion.getErrores().agregarError("Semantico","break","Solo se admite uso de la palabra reservada break, dentro de un caso de un switch BREAK",instruccion.getPosicion());
                }else{
                    if((i+1) < instrucciones.size())
                        coleccion.getErrores().agregarError("Semantico","break","Existen instrucciones inalcanzables limitados por la sentencia BREAK",instruccion.getPosicion());
                }
            }else if(instruccion instanceof LibreriaInstr){
                //se agrega para un posterior uso(?)
            }
        }
    }
    
}
