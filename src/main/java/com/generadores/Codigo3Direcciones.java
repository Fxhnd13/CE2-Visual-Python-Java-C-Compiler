/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.generadores;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.basicos.Llamadas.*;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Temporal;
import com.generadores.objetos.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Soberanis
 */
public class Codigo3Direcciones {

    public List<Cuarteto> generarCuartetos(Llamada llamada, Coleccion coleccion){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(llamada instanceof LlamadaJava){
            return generarCuartetos((LlamadaJava)llamada, coleccion);
        }else if(llamada instanceof LlamadaPython){
            return generarCuartetos((LlamadaPython)llamada, coleccion);
        }else if(llamada instanceof LlamadaVisual){
            return generarCuartetos((LlamadaVisual)llamada, coleccion);
        }else{
            
        }
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetos(LlamadaJava llamada, Coleccion coleccion){
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
        
        cuartetosRetorno.add(new Cuarteto("call",Utilidades.nombreMetodo(CONST.SEC_JV, simbolo.getTipo(), llamada),String.valueOf(temporalesDeParametros.size()),null));
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,"0",Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto("arreglo","stack",Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
        coleccion.setUltimoReturn(Temporal.actualTemporal());
        cuartetosRetorno.add(new Cuarteto("-",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
                
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetos(LlamadaPython llamada, Coleccion coleccion){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
        List<String> posiciones = obtenerPosiciones(llamada, coleccion, 1);
        
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        for (int i = 1; i < temporalesDeParametros.size(); i++) {
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
        }
        
        cuartetosRetorno.add(new Cuarteto("call",Utilidades.nombreMetodo(CONST.SEC_PY, llamada),String.valueOf(temporalesDeParametros.size()),null));
        cuartetosRetorno.add(new Cuarteto("+","0",CONST.P,Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
        coleccion.setUltimoReturn(Temporal.actualTemporal());
        cuartetosRetorno.add(new Cuarteto("-",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        
        return cuartetosRetorno;
    }
    
    private List<Cuarteto> generarCuartetos(LlamadaVisual llamada, Coleccion coleccion){
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        List<String> temporalesDeParametros = obtenerTemporalesDeParametros(cuartetosRetorno, llamada, coleccion);
        List<String> posiciones = obtenerPosiciones(llamada, coleccion, 1);
        
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        for (int i = 1; i < temporalesDeParametros.size(); i++) {
            cuartetosRetorno.add(new Cuarteto("+",CONST.P,posiciones.get(i),Temporal.siguienteTemporal(CONST.ENTERO)));
            cuartetosRetorno.add(new Cuarteto(":=a",temporalesDeParametros.get(i),Temporal.actualTemporal(),CONST.STACK));
        }
        
        cuartetosRetorno.add(new Cuarteto("call",Utilidades.nombreMetodo(CONST.SEC_VB, llamada),String.valueOf(temporalesDeParametros.size()),null));
        cuartetosRetorno.add(new Cuarteto("+","0",CONST.P,Temporal.siguienteTemporal(CONST.ENTERO)));
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
        coleccion.setUltimoReturn(Temporal.actualTemporal());
        cuartetosRetorno.add(new Cuarteto("-",CONST.P,coleccion.getSimbolos().getUltimaPosicionLibre(cuartetosRetorno),CONST.P));
        
        return cuartetosRetorno;
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

    private List<String> obtenerPosiciones(Llamada llamada, Coleccion coleccion, int a) {
        List<String> posiciones = new ArrayList();
        for (int i = 0; i < a; i++) {
            posiciones.add(String.valueOf(i));
        }
        for (int i = 0; i < llamada.getParametros().size(); i++) {
            posiciones.add(String.valueOf(a+i));
        }
        return posiciones;
    }
}
