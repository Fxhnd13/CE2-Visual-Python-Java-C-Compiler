/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoBooleano;
import com.analisis.semantico.AnalizadorBloque;
import com.generadores.Codigo3Direcciones;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada al almacenamiento de la informacion para una instruccion do...while
 * @author jose_
 */
public class DoWhileInstr implements Instruccion{
    
    private NodoBooleano condiciones;
    private List<Instruccion> instrucciones;
    private Pos posicion;

    public DoWhileInstr() {
    }

    public DoWhileInstr(NodoBooleano condiciones, List<Instruccion> instrucciones, Pos posicion) {
        this.condiciones = condiciones;
        this.instrucciones = instrucciones;
        this.posicion = posicion;
    }

    public NodoBooleano getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(NodoBooleano condiciones) {
        this.condiciones = condiciones;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Pos getPosicion() {
        return posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }

    @Override
    public void generarCodigoAssembler(Coleccion coleccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuarteto> generarCuartetos(Coleccion coleccion) {
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        
        coleccion.getSimbolos().agregarAmbitoTemporal();
        Codigo3Direcciones generador = new Codigo3Direcciones();
        List<Cuarteto> cuartetosInstrucciones = generador.generarCodigo3Direcciones(instrucciones, coleccion);
        coleccion.getSimbolos().eliminarAmbitoTemporal();
        
        List<Cuarteto> cuartetosCondicional = condiciones.generarCuartetos(coleccion);

        String etiquetaInicio = condiciones.getEtiquetaSi();
        cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaInicio));
        String etiquetaFinal = condiciones.getEtiquetaNo();


        Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosInstrucciones);
        Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosCondicional);
        cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaFinal));
        
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        this.condiciones.analizarSemanticamente(coleccion);
        coleccion.getSimbolos().agregarAmbitoTemporal();
        AnalizadorBloque analizador = new AnalizadorBloque();
        analizador.analizarBloque(instrucciones, coleccion);
        coleccion.getSimbolos().eliminarAmbitoTemporal();
    }
    
}
