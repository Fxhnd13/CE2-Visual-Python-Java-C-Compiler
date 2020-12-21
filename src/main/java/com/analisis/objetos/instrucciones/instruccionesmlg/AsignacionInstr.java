/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.Llamadas.LlamadaJava;
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
import com.analisis.objetos.estructuras.Metodo;
import com.analisis.objetos.estructuras.TablaDeTipos;
import com.analisis.objetos.nodos.Hoja;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.analisis.semantico.AnalizadorLlamadaMetodo;
import com.generadores.Codigo3Direcciones;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Temporal;
import com.generadores.objetos.Utilidades;
import java.util.ArrayList;
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
        List<Cuarteto> cuartetosRetorno = new ArrayList();

        if(lugar instanceof LugarVariable){

            generarCuartetosDeVariable(cuartetosRetorno, (LugarVariable)lugar, accion, coleccion);

        }else if (lugar instanceof LugarArreglo){

            generarCuartetosDeArreglo(cuartetosRetorno, (LugarArreglo)lugar, accion, coleccion);
            
        }else if(lugar instanceof LugarClase){
            
            generarCuartetosDeClase(cuartetosRetorno, (LugarClase)lugar, accion, coleccion);
            
        }
                
        return cuartetosRetorno;
    }
    
    @Override
    public void generarCodigoAssembler(Coleccion coleccion){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void analizarAsignacionObjeto(LugarClase lugar, Accion accion, Coleccion coleccion){
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugar.getId());
        Clase clase = null;
        if(simbolo!=null){
            clase = (Clase) coleccion.getClasesJv().getSimbolo(simbolo.getTipo()).getValor(); 
        }else{
            coleccion.getErrores().agregarError("Semantico", lugar.getId(), "No existe la variable a la que se desea asignar el valor.", lugar.getPosicion());
        }
        if(accion instanceof AccionExpresion){
            AccionExpresion action = (AccionExpresion) accion;
            if(action.getExpresion() instanceof Hoja){
                Hoja nodo = (Hoja) action.getExpresion();
                if(nodo.getValor().getTipo().equals(CONST.ID)){
                    Simbolo variable = coleccion.getSimbolos().getSimbolo((String) nodo.getValor().getValor());
                    if(variable==null){
                        if(!simbolo.getTipo().equals(variable.getTipo())){
                            //si no son el mismo tipo de clase
                            coleccion.getErrores().agregarError("Semantico",(String)nodo.getValor().getValor(),"Para una asignacion entre objetos deben ser de la misma clase.", nodo.getPosicion());
                        }
                    }else{
                        coleccion.getErrores().agregarError("Semantico", variable.getId(), "No existe la variable utilizada para asignar.", posicion);
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
            AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
            analizador.analizarLlamadaMetodoJavaConReturn(action.getConstructor(), coleccion);
            if(simbolo!=null){
                if(action.getConstructor().getIdMetodo()==null)action.getConstructor().setIdMetodo(simbolo.getTipo());
                Simbolo metodo = Utilidades.existeMetodo(clase.getMetodos(),simbolo.getTipo(), action.getConstructor());
                if(metodo == null){
                    coleccion.getErrores().agregarError("Semantico","Sin Cadena","No existe un constructor con los parametros indicados",action.getPosicion());
                }else{
                    simbolo.setValor(new Object());
                }
            }
        }
    }
    
    private void analizarAsignacionVariable(Lugar lugar, Accion accion, Coleccion coleccion){
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
            lugar = new LugarVariableGlobal(lugar.getId(),lugar.getPosicion());
            analizarAsignacionVariableGlobal((LugarVariableGlobal)lugar, accion, coleccion);
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

    private void generarCuartetosDeVariable(List<Cuarteto> cuartetosRetorno, LugarVariable lugar, Accion accion, Coleccion coleccion) {
        
        if(accion instanceof AccionIngreso){

            if(((AccionIngreso)accion).getMensaje()!=null){
                Cuartetos.unirCuartetos(cuartetosRetorno, ((AccionIngreso)accion).getMensaje().generarCuartetos(coleccion));
            }
            String posicion = coleccion.getSimbolos().getSimbolo(lugar.getId()).getDireccion();
            cuartetosRetorno.add(new Cuarteto("read",null,null,Temporal.siguienteTemporal(((AccionIngreso)accion).getTipoRetorno())));
            String valor = Temporal.actualTemporal();
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posicion,Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",valor,Temporal.actualTemporal(),CONST.STACK));

        }else if(accion instanceof AccionExpresion){

            NodoAritmetico exp = ((AccionExpresion)accion).getExpresion();

            List<Cuarteto> cuartetosExpresion = new ArrayList();

            cuartetosExpresion = exp.generarCuartetos(coleccion);
            String temporalExp = Temporal.actualTemporal();
            Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosExpresion);
            if(lugar instanceof LugarVariable){

                String posicion = null;
                if(coleccion.getSimbolos().getSimbolo(lugar.getId()) != null){
                    posicion = coleccion.getSimbolos().getSimbolo(lugar.getId()).getDireccion();
                    cuartetosRetorno.add(new Cuarteto("+",CONST.P,posicion,Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto(":=a",(coleccion.getUltimoReturn()==null)?temporalExp:coleccion.getUltimoReturn(),Temporal.actualTemporal(),CONST.STACK));
                    coleccion.setUltimoReturn(null);
                }else{
                    posicion = ((Clase)coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor()).getSimbolos().getSimbolo(lugar.getId()).getDireccion();
                    cuartetosRetorno.add(new Cuarteto("+",CONST.P,"0",Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(), posicion, Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto(":=a",(coleccion.getUltimoReturn()==null)?temporalExp:coleccion.getUltimoReturn(),Temporal.actualTemporal(),CONST.HEAP));
                    coleccion.setUltimoReturn(null);
                }

            }
        }
    }

    private void generarCuartetosDeArreglo(List<Cuarteto> cuartetosRetorno, LugarArreglo lugarArreglo, Accion accion, Coleccion coleccion) {
        
        List<String> temporalesDeIndices = new ArrayList();
        Arreglo arregloDatos = (Arreglo) coleccion.getSimbolos().getSimbolo(lugarArreglo.getId()).getValor();
        
        for (NodoAritmetico expresion : lugarArreglo.getIndices()) {
            for (Cuarteto cuarteto : expresion.generarCuartetos(coleccion)) {
                cuartetosRetorno.add(cuarteto);
            }
            temporalesDeIndices.add(Temporal.actualTemporal());
        }
        
        for (int i = 0; i < arregloDatos.getTemporales().size(); i++) {
            if(i==0){
                cuartetosRetorno.add(new Cuarteto(":=",temporalesDeIndices.get(i),null,Temporal.siguienteTemporal(CONST.ENTERO)));
            }else{
                cuartetosRetorno.add(new Cuarteto("*",Temporal.actualTemporal(), arregloDatos.getTemporales().get(i-1),Temporal.siguienteTemporal(CONST.ENTERO)));
                cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(),temporalesDeIndices.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            }
        }
        
        String posicionStack = coleccion.getSimbolos().getSimbolo(lugar.getId()).getDireccion();
        cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(),posicionStack, Temporal.siguienteTemporal(CONST.ENTERO)));
        String posicionStackFinal = Temporal.actualTemporal();

        if(accion instanceof AccionIngreso){

            if(((AccionIngreso)accion).getMensaje()!=null){
                Cuartetos.unirCuartetos(cuartetosRetorno, ((AccionIngreso)accion).getMensaje().generarCuartetos(coleccion));
            }
            cuartetosRetorno.add(new Cuarteto("read",null,posicionStackFinal,CONST.STACK));

        }else if(accion instanceof AccionExpresion){

            List<Cuarteto> cuartetosExpresion = ((AccionExpresion)accion).getExpresion().generarCuartetos(coleccion);
            Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosExpresion);
            cuartetosRetorno.add(new Cuarteto(":=a",(coleccion.getUltimoReturn()==null)?Temporal.actualTemporal():coleccion.getUltimoReturn(),posicionStackFinal,CONST.STACK));
            coleccion.setUltimoReturn(null);
        }
    }

    private void generarCuartetosDeClase(List<Cuarteto> cuartetosRetorno, LugarClase lugarClase, Accion accion, Coleccion coleccion) {
            
        if(accion instanceof AccionExpresion){

            String direccionHeap = coleccion.getSimbolos().getSimbolo(lugarClase.getId()).getDireccion();
            coleccion.getSimbolos().getSimbolo(lugarClase.getId()).setDireccion(direccionHeap);

        }else{

            LlamadaJava llamada = ((AccionConstructor)accion).getConstructor();
            List<String> temporalesParametros = new ArrayList(); //temporales que almacenan los parametros a enviar
            Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugar.getId());//simbolo del objeto (obj1, algo1, etc)
            
            for (NodoAritmetico parametro : llamada.getParametros()) {
                for (Cuarteto cuarteto : parametro.generarCuartetos(coleccion)) {
                    cuartetosRetorno.add(cuarteto);
                }
                temporalesParametros.add(Temporal.actualTemporal());
            }

            Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(simbolo.getTipo()).getValor();

            asignarEspaciosEnMemoria:{ //revisar correctamente al hacer pruebas 

                String temporalDireccion = coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno);

                //cuartetosRetorno.add(new Cuarteto("+",CONST.P, temporalDireccion, Temporal.siguienteTemporal(CONST.ENTERO)));
                cuartetosRetorno.add(new Cuarteto(":=a",CONST.H,temporalDireccion,CONST.STACK));
                
                if(clase.getSimbolos().getSimbolos().size() > 0 ){
                    cuartetosRetorno.add(new Cuarteto("+",String.valueOf(clase.getSimbolos().getSimbolos().size()),CONST.H,CONST.H));
                }else{
                    cuartetosRetorno.add(new Cuarteto("+","1",CONST.H,CONST.H));
                }

                simbolo.setDireccion(Temporal.actualTemporal());
            }
            
            Codigo3Direcciones generador = new Codigo3Direcciones();
            List<String> posiciones = generador.obtenerPosiciones(llamada, coleccion, 1);

            cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,simbolo.getDireccion(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
            String valorThis = Temporal.actualTemporal();
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
            cuartetosRetorno.add(new Cuarteto("+","0",CONST.P, Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",valorThis,Temporal.actualTemporal(),CONST.STACK));
            
            for (int i=1; i<temporalesParametros.size(); i++) {
                cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
                cuartetosRetorno.add(new Cuarteto(":=a",Temporal.actualTemporal(),temporalesParametros.get(i),CONST.STACK));
            }
            
            String idMetodo = Utilidades.nombreMetodo(CONST.SEC_JV, simbolo.getTipo(), llamada);
            cuartetosRetorno.add(new Cuarteto("call",idMetodo,String.valueOf(temporalesParametros.size()),null));
            cuartetosRetorno.add(new Cuarteto("-",String.valueOf(coleccion.getSimbolos().getSimbolos().size()),CONST.P,CONST.P));
        }
    }
}
