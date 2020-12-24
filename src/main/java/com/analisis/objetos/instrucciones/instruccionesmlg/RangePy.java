/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.nodos.Hoja;
import com.analisis.objetos.nodos.NodoAritmetico;

/**
 *
 * @author Jose Soberanis
 */
public class RangePy {

    private NodoAritmetico aumento, inicio, fin;
    
    public RangePy(NodoAritmetico inicio, NodoAritmetico fin, NodoAritmetico aumento){
        this.inicio = inicio;
        this.fin = fin;
        this.aumento = aumento;
    }
    
    public RangePy(NodoAritmetico inicio, NodoAritmetico fin){
        this(inicio, fin, new Hoja(new Dato(CONST.ENTERO, "1"), new Pos()));
    }
    
    public RangePy(NodoAritmetico fin){
        this(new Hoja(new Dato(CONST.ENTERO,"0"), new Pos()), fin, new Hoja(new Dato(CONST.ENTERO, "1"), new Pos()));
    }

    public NodoAritmetico getAumento() {
        return aumento;
    }

    public void setAumento(NodoAritmetico aumento) {
        this.aumento = aumento;
    }

    public NodoAritmetico getInicio() {
        return inicio;
    }

    public void setInicio(NodoAritmetico inicio) {
        this.inicio = inicio;
    }

    public NodoAritmetico getFin() {
        return fin;
    }

    public void setFin(NodoAritmetico fin) {
        this.fin = fin;
    }
    
}
