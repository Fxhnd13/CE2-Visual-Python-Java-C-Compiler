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
import com.generadores.objetos.Etiqueta;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion Si... o Sino Si...
 * @author jose_
 */
public class SiInstr implements InstruccionSino{
    
    private NodoBooleano condicion;
    private List<Instruccion> instrucciones;
    private InstruccionSino instruccionSino;
    private Pos posicion;

    public SiInstr() {
    }

    public SiInstr(NodoBooleano condicion, List<Instruccion> instruccionesSi, InstruccionSino instruccionSino, Pos posicion) {
        this.condicion = condicion;
        this.instrucciones = instruccionesSi;
        this.instruccionSino = instruccionSino;
        this.posicion = posicion;
    }

    public NodoBooleano getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoBooleano condicion) {
        this.condicion = condicion;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instruccionesSi) {
        this.instrucciones = instruccionesSi;
    }

    public InstruccionSino getInstruccionSino() {
        return instruccionSino;
    }

    public void setInstruccionSino(InstruccionSino instruccionSino) {
        this.instruccionSino = instruccionSino;
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
        String etiquetaSalida = Etiqueta.siguienteEtiqueta();
        Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosSiInstr(this, etiquetaSalida, coleccion));
        cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaSalida));
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        condicion.analizarSemanticamente(coleccion);
        coleccion.getSimbolos().agregarAmbitoTemporal();
        AnalizadorBloque analizador = new AnalizadorBloque();
        analizador.analizarBloque(instrucciones, coleccion);
        if(instruccionSino!=null){
            instruccionSino.analizarSemanticamente(coleccion);
        }
    }

    private List<Cuarteto> cuartetosSiInstr(InstruccionSino instruccion, String etiquetaSalida, Coleccion coleccion) {
        
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        String etiquetaSi = null, etiquetaSino = null;
        Codigo3Direcciones generador = new Codigo3Direcciones();
        
        if(instruccion instanceof SiInstr){
            
            Cuartetos.unirCuartetos(cuartetosRetorno, ((SiInstr)instruccion).getCondicion().generarCuartetos(coleccion));
            etiquetaSi = ((SiInstr)instruccion).getCondicion().getEtiquetaSi();
            etiquetaSino = ((SiInstr)instruccion).getCondicion().getEtiquetaNo();
            cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaSi));
            coleccion.getSimbolos().agregarAmbitoTemporal();
            Cuartetos.unirCuartetos(cuartetosRetorno, generador.generarCodigo3Direcciones(instruccion.getInstrucciones(), coleccion));
            coleccion.getSimbolos().eliminarAmbitoTemporal();
            cuartetosRetorno.add(new Cuarteto("goto",null,null,etiquetaSalida));
            cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaSino));
            
            if(((SiInstr)instruccion).getInstruccionSino()!=null){
                Cuartetos.unirCuartetos(cuartetosRetorno, cuartetosSiInstr(((SiInstr)instruccion).getInstruccionSino(), etiquetaSalida, coleccion));
            }
            
        }else{
            
            coleccion.getSimbolos().agregarAmbitoTemporal();
            Cuartetos.unirCuartetos(cuartetosRetorno, generador.generarCodigo3Direcciones(instruccion.getInstrucciones(), coleccion));
            coleccion.getSimbolos().eliminarAmbitoTemporal();
            
        }
        
        return cuartetosRetorno;
    }
    
}
