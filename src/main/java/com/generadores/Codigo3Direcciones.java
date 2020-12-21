/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.generadores;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Llamadas.*;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.estructuras.Arreglo;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import com.analisis.objetos.estructuras.TablaDeSimbolos;
import com.analisis.objetos.instrucciones.instruccionesmlg.*;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Etiqueta;
import com.generadores.objetos.Temporal;
import com.generadores.objetos.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Soberanis
 */
public class Codigo3Direcciones {

    public List<Cuarteto> generarCuartetos(Llamada llamada, Coleccion coleccion, boolean conReturn){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(llamada instanceof LlamadaJava){
            return generarCuartetosLlamadaJava((LlamadaJava)llamada, coleccion, conReturn);
        }else if(llamada instanceof LlamadaPython){
            return generarCuartetosLlamadaPython((LlamadaPython)llamada, coleccion, conReturn);
        }else if(llamada instanceof LlamadaVisual){
            return generarCuartetosLlamadaVisual((LlamadaVisual)llamada, coleccion, conReturn);
        }else{
            switch(coleccion.getTipadoActual()){
                case 0: return generarCuartetosLlamadaVisual(new LlamadaVisual(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion, conReturn);
                case 1:{
                    List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
                    List<String> posiciones = obtenerPosiciones(llamada, coleccion, 2);

                    cuartetosRetorno.add(new Cuarteto("+","0",CONST.P,Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
                    String valorThis = Temporal.actualTemporal();
                    cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
                    cuartetosRetorno.add(new Cuarteto("+","0",CONST.P, Temporal.siguienteTemporal(CONST.ENTERO)));
                    cuartetosRetorno.add(new Cuarteto(":=a",valorThis,Temporal.actualTemporal(),CONST.STACK));


                    for (int i = 2; i < temporalesDeParametros.size(); i++) {
                        cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
                        cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
                    }

                    agregarCuartetosDeLlamada(cuartetosRetorno, llamada, CONST.SEC_JV, temporalesDeParametros.size(), coleccion, conReturn);
                    break;
                }
                case 2: return generarCuartetosLlamadaPython(new LlamadaPython(llamada.getIdMetodo(), llamada.getParametros(), llamada.getPosicion()), coleccion, conReturn);
            }
        }
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetosLlamadaJava(LlamadaJava llamada, Coleccion coleccion, boolean conReturn){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
        List<String> posiciones = obtenerPosiciones(llamada, coleccion, 2);
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(llamada.getIdVariable());
        
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,simbolo.getDireccion(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
        String valorThis = Temporal.actualTemporal();
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        cuartetosRetorno.add(new Cuarteto("+","0",CONST.P, Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto(":=a",valorThis,Temporal.actualTemporal(),CONST.STACK));
                
                
        for (int i = 2; i < temporalesDeParametros.size(); i++) {
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
        }
        
        agregarCuartetosDeLlamada(cuartetosRetorno, llamada, CONST.SEC_JV, temporalesDeParametros.size(), coleccion, conReturn);
                
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetosLlamadaPython(LlamadaPython llamada, Coleccion coleccion, boolean conReturn){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
        List<String> posiciones = obtenerPosiciones(llamada, coleccion, 1);
        
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        for (int i = 1; i < temporalesDeParametros.size(); i++) {
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
        }
        
        agregarCuartetosDeLlamada(cuartetosRetorno, llamada, CONST.SEC_PY, temporalesDeParametros.size(), coleccion, conReturn);
        
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetosLlamadaVisual(LlamadaVisual llamada, Coleccion coleccion, boolean conReturn){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
        List<String> posiciones = obtenerPosiciones(llamada, coleccion, 1);
        
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        for (int i = 1; i < temporalesDeParametros.size(); i++) {
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
        }
        
        agregarCuartetosDeLlamada(cuartetosRetorno, llamada, CONST.SEC_VB, temporalesDeParametros.size(), coleccion, conReturn);
        
        return cuartetosRetorno;
    }

    private void agregarCuartetosDeLlamada(List<Cuarteto> cuartetosRetorno, Llamada llamada, String seccion, int parametros, Coleccion coleccion, boolean conReturn){        
        cuartetosRetorno.add(new Cuarteto("call",Utilidades.nombreMetodo(seccion, llamada),String.valueOf(parametros),null));
        if(conReturn){
            cuartetosRetorno.add(new Cuarteto("+","0",CONST.P,Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
            coleccion.setUltimoReturn(Temporal.actualTemporal());
        }
        cuartetosRetorno.add(new Cuarteto("-",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
    }
    
    private List<String> obtenerTemporalesDeParametros(List<Cuarteto> cuartetosRetorno, Llamada llamada, Coleccion coleccion) {
        List<String> temporales = new ArrayList();
        for (NodoAritmetico parametro : llamada.getParametros()) {
            for (Cuarteto cuarteto : parametro.generarCuartetos(coleccion)) {
                cuartetosRetorno.add(cuarteto);
            }
            temporales.add(Temporal.actualTemporal());
        }
        return temporales;
    }

    public List<String> obtenerPosiciones(Llamada llamada, Coleccion coleccion, int a) {
        List<String> posiciones = new ArrayList();
        for (int i = 0; i < a; i++) {
            posiciones.add(String.valueOf(i));
        }
        for (int i = 0; i < llamada.getParametros().size(); i++) {
            posiciones.add(String.valueOf(a+i));
        }
        return posiciones;
    }

    public void generarCuartetos(List<Cuarteto> cuartetosRetorno, LugarArreglo lugarArreglo, Coleccion coleccion) {
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugarArreglo.getId()); //para obtener la variable
        Arreglo datosArreglo = (Arreglo) simbolo.getValor(); //para obtener las etiquetas de las dimensiones
        List<String> temporalesDeIndices = new ArrayList();
        
        //generamos el codigo 3 direcciones de cada expresion enviada como indice
        for (NodoAritmetico indice : lugarArreglo.getIndices()) {
            Cuartetos.unirCuartetos(cuartetosRetorno, indice.generarCuartetos(coleccion));
            temporalesDeIndices.add(Temporal.actualTemporal());
        }
        
        //obtenemos el indice real
        for (int i = 0; i < datosArreglo.getTemporales().size(); i++) {
            if(i==0){
                cuartetosRetorno.add(new Cuarteto(":=",temporalesDeIndices.get(i),null,Temporal.siguienteTemporal(CONST.ENTERO)));
            }else{
                cuartetosRetorno.add(new Cuarteto("*",Temporal.actualTemporal(), datosArreglo.getTemporales().get(i-1),Temporal.siguienteTemporal(CONST.ENTERO)));
                cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(), temporalesDeIndices.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            }
        }        

        //obtenemos el valor del stack
        cuartetosRetorno.add(new Cuarteto("+",simbolo.getDireccion(),Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
    }

    public List<Cuarteto> generarCodigo(ColeccionInstr instrucciones, Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        coleccion.setTipadoActual(0);
        for (Instruccion instruccion : instrucciones.getInstruccionesVb()) {
            MetodoInstr instr = (MetodoInstr) instruccion;
            coleccion.setSimbolos(new TablaDeSimbolos());
            if(!instr.getTipoRetorno().equals(CONST.VOID)){
                coleccion.getSimbolos().agregarSimbolo(new Simbolo(CONST.RETURN,CONST.VAR,instr.getTipoRetorno(),"1","0",null,null));
                coleccion.getSimbolos().agregarSimbolo(new Simbolo(instr.getId(),CONST.VAR,instr.getTipoRetorno(),"1","1",null,null));
                coleccion.setEtiquetaReturn(Etiqueta.siguienteEtiqueta());
            }
            for (Dato parametro : instr.getParametros()) {
                coleccion.getSimbolos().agregarSimboloSiNoExiste(new Simbolo((String)parametro.getValor(),CONST.VAR,parametro.getTipo(),"1",String.valueOf(coleccion.getSimbolos().getSimbolos().size()),null,null));
            }
            cuartetosRetorno.add(new Cuarteto("metodo",null,null,Utilidades.nombreMetodo(CONST.SEC_VB, instr)));
            Cuartetos.unirCuartetos(cuartetosRetorno, generarCodigo3Direcciones(instr.getInstrucciones(), coleccion));
            if(!instr.getTipoRetorno().equals(CONST.VOID))cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,coleccion.getEtiquetaReturn()));
            if(cuartetosRetorno.get(cuartetosRetorno.size()-1).getOp().equals("etiqueta")) cuartetosRetorno.add(new Cuarteto(":=","0",null,"t00"));
            cuartetosRetorno.add(new Cuarteto("finMetodo",null,null,null));
            cuartetosRetorno.add(new Cuarteto("vacio",null,null,null));
        }
        
        coleccion.setTipadoActual(1);
        for (Instruccion instruccion : instrucciones.getInstruccionesJv()) {
            ClaseInstr claseInstruccion = (ClaseInstr) instruccion;
            coleccion.setClase(claseInstruccion.getId());
            for (Instruccion instruccionDeClase : claseInstruccion.getInstrucciones()) {
                if(instruccionDeClase instanceof MetodoInstr){
                    MetodoInstr instr = (MetodoInstr) instruccionDeClase;
                    coleccion.setSimbolos(new TablaDeSimbolos());
                    if(!instr.getTipoRetorno().equals(CONST.VOID)){
                        coleccion.getSimbolos().agregarSimbolo(new Simbolo(CONST.THIS,CONST.REFERENCIA,CONST.ENTERO,"1","0",null,null));
                        coleccion.getSimbolos().agregarSimbolo(new Simbolo(CONST.RETURN,CONST.VAR,instr.getTipoRetorno(),"1","1",null,null));
                        coleccion.setEtiquetaReturn(Etiqueta.siguienteEtiqueta());
                    }
                    for (Dato parametro : instr.getParametros()) {
                        coleccion.getSimbolos().agregarSimboloSiNoExiste(new Simbolo((String)parametro.getValor(),CONST.VAR,parametro.getTipo(),"1",String.valueOf(coleccion.getSimbolos().getSimbolos().size()),null,null));
                    }
                    
                    cuartetosRetorno.add(new Cuarteto("metodo",null,null,Utilidades.nombreMetodo(CONST.SEC_JV, instr)));
                    Cuartetos.unirCuartetos(cuartetosRetorno, generarCodigo3Direcciones(instr.getInstrucciones(), coleccion));
                    if(!instr.getTipoRetorno().equals(CONST.VOID))cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,coleccion.getEtiquetaReturn()));
                    if(cuartetosRetorno.get(cuartetosRetorno.size()-1).getOp().equals("etiqueta")) cuartetosRetorno.add(new Cuarteto(":=","0",null,"t00"));
                    cuartetosRetorno.add(new Cuarteto("finMetodo",null,null,null));
                    cuartetosRetorno.add(new Cuarteto("vacio",null,null,null));
                }
            }
            coleccion.setClase(null);
        }
        
        coleccion.setTipadoActual(2);
        for (Instruccion instruccion : instrucciones.getInstruccionesPy()) {
            MetodoInstr instr = (MetodoInstr) instruccion;
            coleccion.setSimbolos(new TablaDeSimbolos());
            coleccion.getSimbolos().agregarSimbolo(new Simbolo(CONST.RETURN,CONST.VAR,instr.getTipoRetorno(),"1","0",null,null));
            coleccion.setEtiquetaReturn(Etiqueta.siguienteEtiqueta());
            for (Dato parametro : instr.getParametros()) {
                coleccion.getSimbolos().agregarSimboloSiNoExiste(new Simbolo((String)parametro.getValor(),CONST.VAR,parametro.getTipo(),"1",String.valueOf(coleccion.getSimbolos().getSimbolos().size()),null,null));
            }
            cuartetosRetorno.add(new Cuarteto("metodo",null,null,Utilidades.nombreMetodo(CONST.SEC_VB, instr)));
            Cuartetos.unirCuartetos(cuartetosRetorno, generarCodigo3Direcciones(instr.getInstrucciones(), coleccion));
            cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,coleccion.getEtiquetaReturn()));
            cuartetosRetorno.add(new Cuarteto(":=","0",null,"t00"));
            cuartetosRetorno.add(new Cuarteto("finMetodo",null,null,null));
            cuartetosRetorno.add(new Cuarteto("vacio",null,null,null));
        }
        
        coleccion.setTipadoActual(3);
        for (Instruccion instruccion : instrucciones.getInstruccionesPr()) {

            if(instruccion instanceof DeclaracionInstr){

                Cuartetos.unirCuartetos(cuartetosRetorno, ((DeclaracionInstr)instruccion).generarCuartetos(coleccion));

            }else if(instruccion instanceof AsignacionInstr) Cuartetos.unirCuartetos(cuartetosRetorno, ((AsignacionInstr)instruccion).generarCuartetos(coleccion));
        }
        
        cuartetosRetorno.add(new Cuarteto("vacio",null,null,null));
        cuartetosRetorno.add(new Cuarteto("main",null,null,null));
        for (Instruccion instruccion : instrucciones.getInstruccionesPr()) {
            if(instruccion instanceof MetodoInstr) Cuartetos.unirCuartetos(cuartetosRetorno, generarCodigo3Direcciones(((MetodoInstr)instruccion).getInstrucciones(), coleccion));
        }
        
        cuartetosRetorno.add(new Cuarteto("return",null,null,null));
        cuartetosRetorno.add(new Cuarteto("finMetodo",null,null,null));
        
        return cuartetosRetorno;
    }

    public List<Cuarteto> generarCodigo3Direcciones(List<Instruccion> instrucciones, Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        for (Instruccion instruccion : instrucciones) {
            
            if(instruccion instanceof DeclaracionInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((DeclaracionInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof AsignacionInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((AsignacionInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof IngresoInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno,((IngresoInstr)instruccion).generarCuartetos(coleccion));
                        
            }else if(instruccion instanceof DoWhileInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((DoWhileInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof WhileInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((WhileInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof ForInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((ForInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof LlamadaInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((LlamadaInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof MensajeInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((MensajeInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof SiInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((SiInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof SwitchInstr){
                
                Cuartetos.unirCuartetos(cuartetosRetorno, ((SwitchInstr)instruccion).generarCuartetos(coleccion));
                
            }else if(instruccion instanceof ReturnInstr){
             
                Cuartetos.unirCuartetos(cuartetosRetorno, ((ReturnInstr)instruccion).generarCuartetos(coleccion));
                
            }
        }
        return cuartetosRetorno;
    }
}
