/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.accionesAsignacion.Accion;
import com.analisis.objetos.basicos.accionesAsignacion.AccionConstructor;
import com.analisis.objetos.basicos.accionesAsignacion.AccionExpresion;
import com.analisis.objetos.basicos.accionesAsignacion.AccionIngreso;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarClase;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarVariable;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarVariableGlobal;
import com.analisis.objetos.estructuras.Arreglo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.TablaDeTipos;
import com.analisis.objetos.nodos.Hoja;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Utilidades;
import java.util.List;
import org.xml.sax.SAXNotSupportedException;

/**
 * Clase destinada a almacenar la informacion de una instruccion de asignacion
 * @author jose_
 */
public class AsignacionInstr implements Instruccion{
    
    private Lugar lugar;
    private Accion accion;
    private Pos posicion;

    public AsignacionInstr() {
    }

    public AsignacionInstr(Lugar lugar, Accion accion, Pos posicion) {
        this.lugar = lugar;
        this.accion = accion;
        this.posicion = posicion;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    @Override
    public void analizarSemanticamente(Coleccion coleccion){
        Simbolo variable = null;
        if(coleccion.getClase()!=null){
            variable = coleccion.getSimbolos().getSimbolo(lugar.getId());
        }else{
            variable = ((Clase)coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor()).getSimbolos().getSimbolo(lugar.getId());
        }
        //si existe verificamos que lo que se desea asignar sea de un tipo con sentido
        if(lugar instanceof LugarClase){
            analizarAsignacionObjeto((LugarClase) lugar, accion, coleccion);
        }else if(lugar instanceof LugarVariable){
            analizarAsignacionVariable((LugarVariable) lugar, accion, coleccion);
        }else if(lugar instanceof LugarVariableGlobal){
            analizarAsignacionVariableGlobal((LugarVariableGlobal) lugar, accion, coleccion);
        }else if(lugar instanceof LugarArreglo){
            analizarAsignacionArreglo((LugarArreglo) lugar, accion, coleccion);
        }
    }
    
    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void generarCodigoAssembler(Coleccion coleccion){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void analizarAsignacionObjeto(LugarClase lugar, Accion accion, Coleccion coleccion){
        if(accion instanceof AccionExpresion){
            AccionExpresion action = (AccionExpresion) accion;
            if(action.getExpresion() instanceof Hoja){
                Hoja nodo = (Hoja) action.getExpresion();
                if(nodo.getValor().getTipo().equals(CONST.ID)){
                    Simbolo variable = coleccion.getSimbolos().getSimbolo((String) nodo.getValor().getValor());
                    if(!lugar.getTipoInstancia().equals(variable.getTipo())){
                        //si no son el mismo tipo de clase
                        coleccion.getErrores().agregarError("Semantico",(String)nodo.getValor().getValor(),"Para una asignacion entre objetos deben ser de la misma clase.", nodo.getPosicion());
                    }
                }else{
                    //no es un identificador
                    coleccion.getErrores().agregarError("Semantico",(String)nodo.getValor().getValor(),"Se desea asignar un valor primitivo a un objeto.", nodo.getPosicion());
                }
            }else{
                //no es un nodo hoja (es decir, es una operacion)
                coleccion.getErrores().agregarError("Semantico","Sin cadena","Se desea asignar una expresion a un objeto.", ((AccionExpresion) accion).getExpresion().getPosicion());
            }
        }else if(accion instanceof AccionConstructor){
            AccionConstructor action = (AccionConstructor) accion;
            if(!coleccion.existeMetodo(action.getConstructor())){
                coleccion.getErrores().agregarError("Semantico",action.getConstructor().getIdMetodo(),"No existe un constructor con los parametros enviados.", action.getPosicion());
            }
//            Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(lugar.getTipoInstancia()).getValor();
//            boolean existeConstructor = false;
//            for (Simbolo simbolo : clase.getMetodos().getSimbolos()) {
//                if(Utilidades.nombreMetodo(CONST.SEC_JV, lugar.getTipoInstancia(), simbolo).equals(
//                Utilidades.nombreMetodo(CONST.SEC_JV, lugar.getTipoInstancia(), ((AccionConstructor)accion).getConstructor()))){
//                    existeConstructor = true; break;
//                }
//            }
//            if(!existeConstructor){
//                //no existe un constructor con los parametros enviados
//            }
        }
    }
    
    private void analizarAsignacionVariable(LugarVariable lugar, Accion accion, Coleccion coleccion){
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugar.getId());
        String tipoParaAsignar = analizarAccionDeVariable(accion, coleccion);
        if(simbolo==null){
            if(coleccion.getTipadoActual()==2){
                simbolo = new Simbolo(lugar.getId(),CONST.VAR,CONST.INDEFINIDO,null,null,null,null);
                coleccion.getSimbolos().agregarSimbolo(simbolo);
            }else if(coleccion.getTipadoActual()!=1){
                coleccion.getErrores().agregarError("Semantico",lugar.getId(),"Se ha utilizado una variable que no ha sido declarada",lugar.getPosicion());
            }
        }
        if(coleccion.getTipadoActual()==1){
            analizarAsignacionVariableGlobal(new LugarVariableGlobal(lugar.getId(), lugar.getPosicion()), accion, coleccion);
        }else{
            if(simbolo!=null){
                TablaDeTipos tablaDeTipos = new TablaDeTipos(posicion);
                tablaDeTipos.evaluarAsignacion(simbolo, tipoParaAsignar, coleccion);
            }
        }
    }
    
    private void analizarAsignacionVariableGlobal(LugarVariableGlobal lugar, Accion accion, Coleccion coleccion){
        Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor();
        Simbolo simbolo = clase.getSimbolos().getSimbolo(lugar.getId());
        if(simbolo==null) coleccion.getErrores().agregarError("Semantico",lugar.getId(),"Se ha utilizado una variable que no ha sido declarada",lugar.getPosicion());
        String tipoParaAsignar = analizarAccionDeVariable(accion, coleccion);
        if(simbolo!=null){
            TablaDeTipos tablaDeTipos = new TablaDeTipos(posicion);
            tablaDeTipos.evaluarAsignacion(simbolo, tipoParaAsignar, coleccion);
        }
    }
    
    private void analizarAsignacionArreglo(LugarArreglo lugar, Accion accion, Coleccion coleccion){
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugar.getId());
        if(simbolo==null) coleccion.getErrores().agregarError("Semantico",lugar.getId(),"Se ha utilizado una variable que no ha sido declarada",lugar.getPosicion());
        String tipoParaAsignar = analizarAccionDeVariable(accion, coleccion);
        if(simbolo!=null){
            Arreglo arreglo = (Arreglo) simbolo.getValor();
            if(arreglo.getDimensiones().size()!=lugar.getIndices().size()){
                coleccion.getErrores().agregarError("Semantico",lugar.getId(),"Las dimensiones con las que se declaro el arreglo no coinciden con las dimensiones utilizadas.", lugar.getPosicion());
            }
            for (NodoAritmetico indice : lugar.getIndices()) {
                if(!indice.analizarSemanticamente(coleccion).getTipo().equals(CONST.ENTERO)){
                    coleccion.getErrores().agregarError("Semantico","Expresion", "La expresion utilizada para una dimension del arreglo no es un entero.", indice.getPosicion());
                }
            }
            TablaDeTipos tablaDeTipos = new TablaDeTipos(posicion);
            tablaDeTipos.evaluarAsignacion(simbolo, tipoParaAsignar, coleccion);
        }
    }

    private String analizarAccionDeVariable(Accion accion, Coleccion coleccion) {
        String tipo = CONST.INDEFINIDO;
        if(accion instanceof AccionExpresion){
            tipo = ((AccionExpresion)accion).getExpresion().analizarSemanticamente(coleccion).getTipo();
        }else if(accion instanceof AccionIngreso){
            ((AccionIngreso)accion).getMensaje().analizarSemanticamente(coleccion);
            tipo = ((AccionIngreso)accion).getTipoRetorno();
        }
        return tipo;
    }
}
