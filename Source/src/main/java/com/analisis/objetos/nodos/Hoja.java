/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.Llamadas.Llamada;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.estructuras.Arreglo;
import com.analisis.objetos.estructuras.Clase;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.semantico.AnalizadorLlamadaMetodo;
import com.generadores.Codigo3Direcciones;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de un nodo hoja, identificador/entero/flotante/caracter/cadena/funcion
 * @author jose_
 */
public class Hoja implements NodoAritmetico{
    
    private Dato valor;
    private String tipoRetorno;
    private Pos posicion;

    public Hoja() {
    }

    public Hoja(Dato valor, Pos posicion) {
        this.valor = valor;
        this.posicion = posicion;
    }
    
    @Override
    public Dato analizarSemanticamente(Coleccion coleccion){
        if(valor.getValor() instanceof Llamada){
            AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
            tipoRetorno = analizador.analizarLLamada((Llamada)valor.getValor(), coleccion);
            if(tipoRetorno!=null){
                return new Dato(tipoRetorno, ((Llamada)valor.getValor()).getIdMetodo());
            }
        }else{
            switch(valor.getTipo()){
                case CONST.ENTERO: tipoRetorno = CONST.ENTERO; return new Dato(tipoRetorno,(String)valor.getValor());
                case CONST.FLOTANTE: tipoRetorno = CONST.FLOTANTE; return new Dato(tipoRetorno,(String)valor.getValor());
                case CONST.CARACTER: tipoRetorno = CONST.CARACTER; return new Dato(tipoRetorno,(String)valor.getValor());
                case CONST.CADENA: tipoRetorno = CONST.CADENA; return new Dato(tipoRetorno,(String)valor.getValor());
                case CONST.ARREGLO:{
                    return analizarArreglo(coleccion);
                }
                case CONST.ID:{
                    Simbolo simbolo = coleccion.getSimbolos().getSimbolo((String)valor.getValor());
                    if(simbolo!=null){
                        this.tipoRetorno = simbolo.getTipo();
                        return new Dato(simbolo.getTipo(),(String)valor.getValor());
                    }else{
                        if(coleccion.getClase()!=null){
                            Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor();
                            simbolo = clase.getSimbolos().getSimbolo((String)valor.getValor());
                            if(simbolo!=null){
                                this.tipoRetorno = simbolo.getTipo();
                                return new Dato(simbolo.getTipo(),(String)valor.getValor());
                            }else{
                                coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"No existe una variable declarada con el identificador utilizado",posicion);
                            }
                        }else{
                            coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"No existe una variable declarada con el identificador utilizado",posicion);
                        }
                    }
                    break;
                }
                case CONST.ID_GLOBAL:{
                    //esta es exclusiva de cuando se esté dentro de una clase
                    Simbolo simbolo = coleccion.getAtributoDeClase((String)valor.getValor());
                    if(simbolo!=null){
                        this.tipoRetorno = simbolo.getTipo();
                        return new Dato(simbolo.getTipo(),(String)valor.getValor());
                    }else{
                        coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"No existe un atributo declarado con el identificador utilizado",posicion);
                    }
                    break;
                }
            }
        }
        return null;
    } //para el primer analisis

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        if(valor.getValor() instanceof Llamada){
            Codigo3Direcciones generador = new Codigo3Direcciones();
            return generador.generarCuartetos((Llamada)valor.getValor(), coleccion, true);
        }else{
            switch(valor.getTipo()){
                case CONST.ENTERO:{
                    cuartetosRetorno.add(new Cuarteto(":=",(String)valor.getValor(),null,Temporal.siguienteTemporal(CONST.ENTERO)));
                    break;
                } 
                case CONST.FLOTANTE: {
                    cuartetosRetorno.add(new Cuarteto(":=",(String)valor.getValor(),null,Temporal.siguienteTemporal(CONST.FLOTANTE)));
                    break;
                }
                case CONST.CARACTER: {
                    cuartetosRetorno.add(new Cuarteto(":=","'"+(String)valor.getValor()+"'",null,Temporal.siguienteTemporal(CONST.CARACTER)));
                    break;
                }
                case CONST.ARREGLO: {
                    Codigo3Direcciones generador = new Codigo3Direcciones();
                    generador.generarCuartetos(cuartetosRetorno, (LugarArreglo)valor.getValor(), coleccion);
                    break;
                }
                case CONST.ID: {
                    Simbolo simbolo = coleccion.getSimbolos().getSimbolo((String)valor.getValor());
                    if(simbolo!=null){
                        cuartetosRetorno.add(new Cuarteto("+",CONST.P,simbolo.getDireccion(),Temporal.siguienteTemporal(CONST.ENTERO)));
                        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(simbolo.getTipo())));
                    }else{
                        generarCuartetosDeLlamadaGlobal(cuartetosRetorno, coleccion);
                    }
                    break;
                }
                case CONST.ID_GLOBAL: {
                    generarCuartetosDeLlamadaGlobal(cuartetosRetorno, coleccion);
                    break;
                }
            }
        }
        return cuartetosRetorno;
    }

    private Dato analizarArreglo(Coleccion coleccion) {
        LugarArreglo lugarArreglo = (LugarArreglo) valor.getValor(); //posicion del arreglo
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugarArreglo.getId()); //datos de la declaracion del arreglo
        if(simbolo != null){
            if(simbolo.getRol().equals(CONST.ARREGLO)){ //verifiamos que la variable exista y si existe que sea un arreglo
                Arreglo arreglo = (Arreglo) simbolo.getValor(); 
                if(arreglo.getDimensiones().size() != lugarArreglo.getIndices().size()){
                    coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"Arreglo de "+arreglo.getDimensiones().size()+" dimensiones, se utilizaron unicamente "+lugarArreglo.getIndices().size()+" indices en la llamada.",this.posicion);
                }
                for (int i=0; i < lugarArreglo.getIndices().size(); i++) {
                    Dato dato = lugarArreglo.getIndices().get(i).analizarSemanticamente(coleccion);
                    if(dato != null){
                        if(!dato.getTipo().equals(CONST.ENTERO)){
                            coleccion.getErrores().agregarError("Semantico","Indice #"+(i+1),"El indice no es un entero.",this.posicion);
                        }
                    }
                }
            }else{
                coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"El identificador utilizado no es un arreglo",this.posicion);
            }
        tipoRetorno = simbolo.getTipo();
        return new Dato(tipoRetorno,null,posicion);
        }else{
            coleccion.getErrores().agregarError("Semantico",(String)valor.getValor(),"No existe una variable con el identificador utilizado",this.posicion);
        }
        return null;
    }

    public Dato getValor() {
        return valor;
    }

    public void setValor(Dato valor) {
        this.valor = valor;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipo) {
        this.tipoRetorno = tipo;
    }

    @Override
    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public Pos getPosicion() {
        return this.posicion;
    }

//    private void generarCuartetosDeLlamadaGlobal(List<Cuarteto> cuartetosRetorno, Coleccion coleccion) {
//        Simbolo simbolo = coleccion.getClasesJv().getSimbolo(coleccion.getClase());
//        Clase clase = (Clase) simbolo.getValor();
//        cuartetosRetorno.add(new Cuarteto("+",CONST.P,"0",Temporal.siguienteTemporal(CONST.ENTERO)));
//        String direccionRelativaHeap = clase.getSimbolos().getSimbolo((String)valor.getValor()).getDireccion();
//        cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(),direccionRelativaHeap,Temporal.siguienteTemporal(CONST.ENTERO)));
//        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.HEAP,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.FLOTANTE)));
//    }
    
    private void generarCuartetosDeLlamadaGlobal(List<Cuarteto> cuartetosRetorno, Coleccion coleccion){
        Clase clase = (Clase) coleccion.getClasesJv().getSimbolo(coleccion.getClase()).getValor();
        Simbolo simbolo = clase.getSimbolos().getSimbolo((String)valor.getValor());
        //t1 = p + <direccion del simbolo> obtenemos la direccion del objeto de la tabla de simbolos
        cuartetosRetorno.add(new Cuarteto("+",CONST.P,"0",Temporal.siguienteTemporal(CONST.ENTERO)));
        //t2 = stack[t1] obtenemos la direccion en el heap del objeto
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.STACK,Temporal.actualTemporal(),Temporal.siguienteTemporal(CONST.ENTERO)));
        //t3 = t2 + <direccion del simbolo en relacion a la clase>
        cuartetosRetorno.add(new Cuarteto("+",Temporal.actualTemporal(), simbolo.getDireccion(), Temporal.siguienteTemporal(CONST.ENTERO)));
        //t4 = heap[t3]
        cuartetosRetorno.add(new Cuarteto("arreglo",CONST.HEAP,Temporal.actualTemporal(),Temporal.siguienteTemporal(simbolo.getTipo())));
    }
    
}
