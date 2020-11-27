/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.nodos;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.ErrorA;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.accionesAsignacion.Llamada;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.estructuras.Arreglo;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.semantico.AnalizadorLlamadaMetodo;

/**
 * Clase destinada a almacenar la informacion de un nodo hoja, identificador/entero/flotante/caracter/cadena/funcion
 * @author jose_
 */
public class Hoja {
    
    private Dato valor;
    private String tipoRetorno;
    private Pos posicion;

    public Hoja() {
    }

    public Hoja(Dato valor, String tipo, Pos posicion) {
        this.valor = valor;
        this.tipoRetorno = tipoRetorno;
        this.posicion = posicion;
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

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    public Dato analizarSemanticamente(Coleccion coleccion){
        switch(valor.getTipo()){
            case CONST.ENTERO: tipoRetorno = CONST.ENTERO; break;
            case CONST.FLOTANTE: tipoRetorno = CONST.FLOTANTE; break;
            case CONST.CARACTER: tipoRetorno = CONST.CARACTER; break;
            case CONST.CADENA: tipoRetorno = CONST.CADENA; break;
            case CONST.ARREGLO:{
                return analizarArreglo(coleccion);
            }
            case CONST.ID:{
                Simbolo simbolo = coleccion.getSimbolos().getSimbolo((String)valor.getValor());
                if(simbolo!=null){
                    this.tipoRetorno = simbolo.getTipo();
                    return new Dato(simbolo.getTipo(),(String)valor.getValor());
                }else{
                    coleccion.getErrores().add(new ErrorA("Semantico",(String)valor.getValor(),"No existe una variable declarada con el identificador utilizado",posicion));
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
                    coleccion.getErrores().add(new ErrorA("Semantico",(String)valor.getValor(),"No existe un atributo declarado con el identificador utilizado",posicion));
                }
                break;
            }
            case CONST.SEC_JV:{
                //llamada a metodo de java (desde clase o desde principal)
                AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
                tipoRetorno = analizador.analizarLLamadaMetodoJavaConReturn((Llamada)valor.getValor(), coleccion);
                break;
            }
            case CONST.SEC_PY:{
                //llamada a metodo de python (desde python o desde principal)
                AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
                tipoRetorno = analizador.analizarLlamadaMetodoPython((Llamada)valor.getValor(), coleccion);
                break;
            }
            case CONST.SEC_VB:{
                //llamada a metodo de visual (desde visual o desde principal)
                AnalizadorLlamadaMetodo analizador = new AnalizadorLlamadaMetodo();
                tipoRetorno = analizador.analizarLlamadaMetodoVisual((Llamada)valor.getValor(), coleccion);
                break;
            }
        }
        return null;
    } //para el primer analisis
    
    public void generarCuartetos(Coleccion coleccion){} //para generar codigo

    private Dato analizarArreglo(Coleccion coleccion) {
        LugarArreglo lugarArreglo = (LugarArreglo) valor.getValor(); //posicion del arreglo
        Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugarArreglo.getId()); //datos de la declaracion del arreglo
        if(simbolo != null){
            if(simbolo.getRol().equals(CONST.ARREGLO)){ //verifiamos que la variable exista y si existe que sea un arreglo
                Arreglo arreglo = (Arreglo) simbolo.getValor(); 
                if(arreglo.getDimensiones().size() != lugarArreglo.getIndices().size()){
                    coleccion.getErrores().add(new ErrorA("Semantico",(String)valor.getValor(),"Arreglo de "+arreglo.getDimensiones().size()+" dimensiones, se utilizaron unicamente "+lugarArreglo.getIndices().size()+" indices en la llamada.",this.posicion));
                }
                for (int i=0; i < lugarArreglo.getIndices().size(); i++) {
                    Dato dato = lugarArreglo.getIndices().get(i).analizarSemanticamente(coleccion);
                    if(dato != null){
                        if(!dato.getTipo().equals(CONST.ENTERO)){
                            coleccion.getErrores().add(new ErrorA("Semantico","Indice #"+(i+1),"El indice no es un entero.",this.posicion));
                        }
                    }
                }
            }else{
                coleccion.getErrores().add(new ErrorA("Semantico",(String)valor.getValor(),"El identificador utilizado no es un arreglo",this.posicion));
            }
        tipoRetorno = simbolo.getTipo();
        return new Dato(tipoRetorno,null,posicion);
        }else{
            coleccion.getErrores().add(new ErrorA("Semantico",(String)valor.getValor(),"No existe una variable con el identificador utilizado",this.posicion));
        }
        return null;
    }
    
}
