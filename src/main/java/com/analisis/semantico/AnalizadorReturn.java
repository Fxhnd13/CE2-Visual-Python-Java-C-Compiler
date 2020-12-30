/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.semantico;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarVariable;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.CaseInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.DeclaracionInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.Instruccion;
import com.analisis.objetos.instrucciones.instruccionesmlg.ReturnInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.SiInstr;
import com.analisis.objetos.instrucciones.instruccionesmlg.SwitchInstr;
import java.util.List;

/**
 * Clase destinada al analisis de los return dentro de un metodo
 * @author jose_
 */
public class AnalizadorReturn {
    
    public boolean tieneReturn(List<Instruccion> instrucciones, Coleccion coleccion, String tipo){
        boolean tieneReturn = false;
        for (int i = 0; i < instrucciones.size(); i++) {
            Instruccion instruccion = instrucciones.get(i);
            if(instruccion instanceof DeclaracionInstr){
                
                DeclaracionInstr instr = (DeclaracionInstr) instruccion;
                Simbolo simboloParaAgregar = new Simbolo(instr.getLugar().getId(),CONST.VAR,instr.getTipo(),"1",null,null,null);
                coleccion.getSimbolos().agregarSimbolo(simboloParaAgregar);
                
            }else if(instruccion instanceof SwitchInstr){
                
                SwitchInstr instr = (SwitchInstr) instruccion;
                boolean returnSwitch = true;
                for (CaseInstr caso : instr.getCasos()) {
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(caso.getInstrucciones(), coleccion, tipo)) returnSwitch = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }
                if(instr.getPorDefecto()!=null){
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(instr.getPorDefecto().getInstrucciones(), coleccion, tipo)) returnSwitch = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }else returnSwitch = false;
                if(returnSwitch) tieneReturn = true;
                
            }else if(instruccion instanceof SiInstr){
                
                SiInstr instr = (SiInstr) instruccion;
                boolean returnSi = true;
                coleccion.getSimbolos().agregarAmbitoTemporal();
                if(!tieneReturn(instr.getInstrucciones(), coleccion, tipo)) returnSi = false;
                coleccion.getSimbolos().agregarAmbitoTemporal();
                if(instr.getInstruccionSino()!=null){
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(instr.getInstruccionSino().getInstrucciones(), coleccion, tipo)) returnSi = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }else returnSi = false;
                if(returnSi) tieneReturn = true;
                
            }else if(instruccion instanceof ReturnInstr){
                
                tieneReturn = true;
                ((ReturnInstr)instruccion).analizarSemanticamente(coleccion, tipo);
                
            }
            if(tieneReturn && ((i+1) < instrucciones.size())){
                coleccion.getErrores().agregarError("Semantico","Return","Existen instrucciones inalcanzables",instrucciones.get(i+1).getPosicion());
            }
        }
        return tieneReturn;
    }
    
    public boolean tieneReturn(List<Instruccion> instrucciones, Coleccion coleccion, String tipo, Simbolo variable){
        boolean tieneReturn = false;
        for (int i = 0; i < instrucciones.size(); i++) {
            Instruccion instruccion = instrucciones.get(i);
            if(instruccion instanceof DeclaracionInstr){
                
                DeclaracionInstr instr = (DeclaracionInstr) instruccion;
                Simbolo simboloParaAgregar = new Simbolo(instr.getLugar().getId(),CONST.VAR,instr.getTipo(),"1",null,null,null);
                coleccion.getSimbolos().agregarSimbolo(simboloParaAgregar);
                
            }else if(instruccion instanceof SwitchInstr){
                
                SwitchInstr instr = (SwitchInstr) instruccion;
                boolean returnSwitch = true;
                for (CaseInstr caso : instr.getCasos()) {
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(caso.getInstrucciones(), coleccion, tipo, variable)) returnSwitch = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }
                if(instr.getPorDefecto()!=null){
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(instr.getPorDefecto().getInstrucciones(), coleccion, tipo, variable)) returnSwitch = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }else returnSwitch = false;
                if(returnSwitch) tieneReturn = true;
                
            }else if(instruccion instanceof SiInstr){
                
                SiInstr instr = (SiInstr) instruccion;
                boolean returnSi = true;
                coleccion.getSimbolos().agregarAmbitoTemporal();
                if(!tieneReturn(instr.getInstrucciones(), coleccion, tipo, variable)) returnSi = false;
                coleccion.getSimbolos().eliminarAmbitoTemporal();
                if(instr.getInstruccionSino()!=null){
                    coleccion.getSimbolos().agregarAmbitoTemporal();
                    if(!tieneReturn(instr.getInstruccionSino().getInstrucciones(), coleccion, tipo, variable)) returnSi = false;
                    coleccion.getSimbolos().eliminarAmbitoTemporal();
                }else returnSi = false;
                if(returnSi) tieneReturn = true;
                
            }else if(instruccion instanceof ReturnInstr){
                
                tieneReturn = true;
                ((ReturnInstr)instruccion).analizarSemanticamente(coleccion, tipo);
                
            }else if(instruccion instanceof AsignacionInstr){
                
                AsignacionInstr instr = (AsignacionInstr) instruccion;
                if(instr.getLugar() instanceof LugarVariable){
                    if(instr.getLugar().getId().equals(variable.getId())) variable.setValor(new Object());
                }
            }
            if(tieneReturn && ((i+1) < instrucciones.size())){
                coleccion.getErrores().agregarError("Semantico","Return","Existen instrucciones inalcanzables",instrucciones.get(i+1).getPosicion());
            }
        }
        return tieneReturn;
    }
}
