/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;

/**
 * Clase destinada al manejo de los tipos dentro del analisis semantico
 * @author jose_
 */
public class TablaDeTipos {
    
    private Pos posicion;
    
    public TablaDeTipos(Pos posicion){
        this.posicion = posicion;
    }
    /**
     *
     * @param operacion 0 multiplicacion, 1 division, 2 modulo, 3 suma, 4 resta
     * @param izquierdo
     * @param derecho
     * @return
     */
    public Dato verificarTiposDeOperacionAritmetica(int operacion, Dato izquierdo, Dato derecho, Coleccion coleccion){
        Dato dato = null;
        switch(operacion){
            case 0: dato = verificarTiposMultiplicacion(izquierdo, derecho, coleccion); break;
            case 1: dato = verificarTiposDivision(izquierdo, derecho, coleccion); break;
            case 2: dato = verificarTiposModulo(izquierdo, derecho, coleccion); break;
            case 3: case 4: dato = verificarTiposSumaResta(izquierdo, derecho, coleccion); break;
        }
        return dato;
    }

    private Dato verificarTiposMultiplicacion(Dato izquierdo, Dato derecho, Coleccion coleccion) {
        boolean izquierdoOperable = false, derechoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '*' no valido para un valor vacio/nulo", posicion);
            }else if(izquierdo.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '*' no valido para un valor/variable tipo Objeto", posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(izquierdo.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '*' no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        izquierdoOperable = true;
                    }
                }else{
                    izquierdoOperable=true;
                }
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '*' no valido para un valor vacio/nulo", posicion);
            }else if(derecho.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '*' no valido para un valor/variable tipo Objeto", posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(derecho.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '*' no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        derechoOperable = true;
                    }
                }else{
                    derechoOperable=true;
                }
            }
        }
        if(izquierdoOperable && derechoOperable){
            if(izquierdo.getTipo().equals(derecho.getTipo())){
                return new Dato(izquierdo.getTipo(), null);
            }else{
                if(izquierdo.getTipo().equals(CONST.INDEFINIDO) || derecho.getTipo().equals(CONST.INDEFINIDO)){
                    return new Dato(CONST.INDEFINIDO, null);
                }else if(izquierdo.getTipo().equals(CONST.FLOTANTE) || derecho.getTipo().equals(CONST.FLOTANTE)){
                    return new Dato(CONST.FLOTANTE, null);
                }else if(izquierdo.getTipo().equals(CONST.ENTERO) || derecho.getTipo().equals(CONST.ENTERO)){
                    return new Dato(CONST.ENTERO, null);
                }else{
                    return new Dato(CONST.CARACTER, null);
                }
            }
        }
        return null;
    }

    private Dato verificarTiposDivision(Dato izquierdo, Dato derecho, Coleccion coleccion) {
        boolean derechoOperable = false;
        boolean izquierdoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '/' no valido para un valor/variable tipo Void.", izquierdo.getPosicion());
            }else if(izquierdo.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '/' no valido para un valor/variable tipo Objeto.", izquierdo.getPosicion());
            }else{
                if((coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2) && izquierdo.getTipo().equals(CONST.CARACTER)){
                    coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '/' no valido para un valor/variable tipo char.", izquierdo.getPosicion());
                }else{
                    izquierdoOperable = true;
                }
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '/' no valido para un valor/variable tipo Void.", derecho.getPosicion());
            }else if(derecho.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '/' no valido para un valor/variable tipo Objeto.", derecho.getPosicion());
            }else{
                if((coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2) && derecho.getTipo().equals(CONST.CARACTER)){
                    coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '/' no valido para un valor/variable tipo char.", derecho.getPosicion());
                }else{
                    izquierdoOperable = true;
                }
            }
        }
        if(izquierdoOperable && derechoOperable){
            if(izquierdo.getTipo().equals(CONST.INDEFINIDO) || derecho.getTipo().equals(CONST.INDEFINIDO)){
                return new Dato(CONST.INDEFINIDO, null, posicion);
            }else if(izquierdo.getTipo().equals(CONST.FLOTANTE) || derecho.getTipo().equals(CONST.FLOTANTE)){
                return new Dato(CONST.FLOTANTE, null, posicion);
            }else if(izquierdo.getTipo().equals(CONST.ENTERO) || derecho.getTipo().equals(CONST.ENTERO)){
                return new Dato(CONST.ENTERO, null, posicion);
            }else{
                return new Dato(CONST.CARACTER, null, posicion);
            }
        }
        return null;
    }

    private Dato verificarTiposModulo(Dato izquierdo, Dato derecho, Coleccion coleccion) {
        boolean izquierdoOperable = false, derechoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador residuo no valido para un valor vacio/nulo", posicion);
            }else if(izquierdo.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador residuo no valido para un valor/variable tipo Objeto",posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(izquierdo.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador residuo no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        izquierdoOperable = true;
                    }
                }else{
                    izquierdoOperable=true;
                }
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador residuo no valido para un valor vacio/nulo", posicion);
            }else if(derecho.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador residuo no valido para un valor/variable tipo Objeto", posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(derecho.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador residuo no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        derechoOperable = true;
                    }
                }else{
                    derechoOperable=true;
                }
            }
        }
        if(izquierdoOperable && derechoOperable){
            if(izquierdo.getTipo().equals(CONST.INDEFINIDO) || derecho.getTipo().equals(CONST.INDEFINIDO)){
                return new Dato(CONST.INDEFINIDO, null);
            }else if(izquierdo.getTipo().equals(CONST.FLOTANTE) || derecho.getTipo().equals(CONST.FLOTANTE)){
                return new Dato(CONST.FLOTANTE, null);
            }else{
                return new Dato(CONST.ENTERO, null);
            }
        }
        return null;
    }

    private Dato verificarTiposSumaResta(Dato izquierdo, Dato derecho, Coleccion coleccion) {
        boolean izquierdoOperable = false, derechoOperable = false;
        if(izquierdo != null){
            if(izquierdo.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '+' no valido para un valor vacio/nulo", posicion);
            }else if(izquierdo.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '+' no valido para un valor/variable tipo Objeto", posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(izquierdo.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) izquierdo.getValor(),"Operador '+' no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        izquierdoOperable = true;
                    }
                }else{
                    izquierdoOperable=true;
                }
            }
        }
        if(derecho != null){
            if(derecho.getTipo().equals(CONST.VOID)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '+' no valido para un valor vacio/nulo", posicion);
            }else if(derecho.getTipo().equals(CONST.CLASE)){
                coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '+' no valido para un valor/variable tipo Objeto", posicion);
            }else{
                if(coleccion.getTipadoActual() == 0 || coleccion.getTipadoActual() == 2){
                    if(derecho.getTipo().equals(CONST.CARACTER)){
                        coleccion.getErrores().agregarError("Semantico",(String) derecho.getValor(),"Operador '+' no valido para un valor/variable tipo caracter.", posicion);
                    }else{
                        derechoOperable = true;
                    }
                }else{
                    derechoOperable=true;
                }
            }
        }
        if(izquierdoOperable && derechoOperable){
            if(izquierdo.getTipo().equals(derecho.getTipo())){
                return new Dato(izquierdo.getTipo(),null);
            }else{
                if(izquierdo.getTipo().equals(CONST.INDEFINIDO) || derecho.getTipo().equals(CONST.INDEFINIDO)){
                    return new Dato(CONST.INDEFINIDO, null);
                }else if(izquierdo.getTipo().equals(CONST.FLOTANTE) || derecho.getTipo().equals(CONST.FLOTANTE)){
                    return new Dato(CONST.FLOTANTE, null);
                }else if(izquierdo.getTipo().equals(CONST.ENTERO) || derecho.getTipo().equals(CONST.ENTERO)){
                    return new Dato(CONST.ENTERO, null);
                }else{
                    return new Dato(CONST.CARACTER, null);
                }
            }
        }
        return null;
    }
}
