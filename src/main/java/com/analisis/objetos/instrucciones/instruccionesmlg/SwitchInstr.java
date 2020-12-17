/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarVariable;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.semantico.AnalizadorBloque;
import com.generadores.objetos.Cuarteto;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion switch
 * @author jose_
 */
public class SwitchInstr implements Instruccion{
     
    private Lugar lugarAsignacion;
    private List<CaseInstr> casos;
    private CaseInstr porDefecto;
    private Pos posicion;

    public SwitchInstr() {
    }

    public SwitchInstr(Lugar lugarAsignacion, List<CaseInstr> casos, CaseInstr porDefecto, Pos posicion) {
        this.lugarAsignacion = lugarAsignacion;
        this.casos = casos;
        this.porDefecto = porDefecto;
        this.posicion = posicion;
    }

    public Lugar getLugarAsignacion() {
        return lugarAsignacion;
    }

    public void setLugarAsignacion(Lugar lugarAsignacion) {
        this.lugarAsignacion = lugarAsignacion;
    }

    public List<CaseInstr> getCasos() {
        return casos;
    }

    public void setCasos(List<CaseInstr> casos) {
        this.casos = casos;
    }

    public CaseInstr getPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(CaseInstr porDefecto) {
        this.porDefecto = porDefecto;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        String tipoEvaluar = retornarTipo(coleccion);
        for (CaseInstr caso : casos) {
            if(tipoEvaluar.equals(caso.getValor().getTipo())) coleccion.getErrores().agregarError("Semantico", (String) caso.getValor().getValor(),"Un valor declarado en la instruccion case no coincide con el tipo de la variable a evaluar. ", caso.getPosicion());
            if(coleccion.getTipadoActual() == 1 || coleccion.getTipadoActual() == 3){
                boolean tieneBreak = false;
                for (int i = 0; i < caso.getInstrucciones().size(); i++) {
                    Instruccion instruccion = caso.getInstrucciones().get(i);
                    if(instruccion instanceof BreakInstr) { tieneBreak = true; break; }
                }
                if(!tieneBreak){
                    //error no tiene break xd
                    coleccion.getErrores().agregarError("Semantico","break","La seccion del case no posee una instruccion break.", caso.getPosicion());
                }
            }
            AnalizadorBloque analizador = new AnalizadorBloque();
            coleccion.getSimbolos().agregarAmbitoTemporal();
            coleccion.setCaso(coleccion.getCaso()+1);
            analizador.analizarBloque(caso.getInstrucciones(), coleccion);
            coleccion.setCaso(coleccion.getCaso()-1);
            coleccion.getSimbolos().eliminarAmbitoTemporal();
        }
    }

    private String retornarTipo(Coleccion coleccion) {
        String tipo = null;
        if(lugarAsignacion instanceof LugarArreglo){
            Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugarAsignacion.getId());
            if(simbolo!=null) tipo = simbolo.getTipo();
        }else if(lugarAsignacion instanceof LugarVariable){
            Simbolo simbolo = coleccion.getSimbolos().getSimbolo(lugarAsignacion.getId());
            if(simbolo!=null) tipo = simbolo.getTipo();
        }
        return tipo;
    }
    
}
