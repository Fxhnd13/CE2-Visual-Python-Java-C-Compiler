/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.analisis.objetos.instrucciones.instruccionesmlg;

/**
 *
 * @author Jose Soberanis
 */
public class RangePy {

    private double aumento, inicio, fin;
    
    public RangePy(double inicio, double fin, double aumento){
        this.inicio = inicio;
        this.fin = fin;
        this.aumento = aumento;
    }
    
    public RangePy(double inicio, double fin){
        this(inicio, fin, 1);
    }
    
    public RangePy(double fin){
        this(0,fin,1);
    }

    public double getAumento() {
        return aumento;
    }

    public void setAumento(double aumento) {
        this.aumento = aumento;
    }

    public double getInicio() {
        return inicio;
    }

    public void setInicio(double inicio) {
        this.inicio = inicio;
    }

    public double getFin() {
        return fin;
    }

    public void setFin(double fin) {
        this.fin = fin;
    }
    
}
