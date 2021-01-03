/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.estructuras;

import com.analisis.objetos.instrucciones.instruccionesmlg.AsignacionInstr;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una clase
 * @author jose_
 */
public class Clase {
    
    private TablaDeSimbolos simbolos, metodos;
    private List<AsignacionInstr> asignaciones;

    public Clase() {
        this.simbolos = new TablaDeSimbolos();
        this.metodos = new TablaDeSimbolos();
        this.asignaciones = new ArrayList();
    }

    public Clase(TablaDeSimbolos simbolos, TablaDeSimbolos metodos) {
        this.simbolos = simbolos;
        this.metodos = metodos;
    }

    public TablaDeSimbolos getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(TablaDeSimbolos simbolos) {
        this.simbolos = simbolos;
    }

    public TablaDeSimbolos getMetodos() {
        return metodos;
    }

    public void setMetodos(TablaDeSimbolos metodos) {
        this.metodos = metodos;
    }

    public List<AsignacionInstr> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionInstr> asignaciones) {
        this.asignaciones = asignaciones;
    }
    
}
