/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.objetos.instrucciones.instruccionesmlg;

import com.analisis.objetos.analisis.CONST;
import com.analisis.objetos.analisis.Pos;
import com.analisis.objetos.basicos.Dato;
import com.analisis.objetos.basicos.Simbolo;
import com.analisis.objetos.basicos.lugaresAsignacion.Lugar;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarArreglo;
import com.analisis.objetos.basicos.lugaresAsignacion.LugarVariable;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.nodos.NodoAritmetico;
import com.analisis.semantico.AnalizadorBloque;
import com.generadores.Codigo3Direcciones;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Etiqueta;
import com.generadores.objetos.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase destinada a almacenar la informacion de una instruccion switch
 * @author jose_
 */
public class SwitchInstr implements Instruccion{
     
    private NodoAritmetico lugarAsignacion;
    private List<CaseInstr> casos;
    private CaseInstr porDefecto;
    private Pos posicion;

    public SwitchInstr() {
    }

    public SwitchInstr(NodoAritmetico lugarAsignacion, List<CaseInstr> casos, CaseInstr porDefecto, Pos posicion) {
        this.lugarAsignacion = lugarAsignacion;
        this.casos = casos;
        this.porDefecto = porDefecto;
        this.posicion = posicion;
    }

    public NodoAritmetico getLugarAsignacion() {
        return lugarAsignacion;
    }

    public void setLugarAsignacion(NodoAritmetico lugarAsignacion) {
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
        List<Cuarteto> cuartetosRetorno = new ArrayList();
        String etiquetaDefault = (porDefecto!=null)? Etiqueta.siguienteEtiqueta() : null;
        String etiquetaFinal = Etiqueta.siguienteEtiqueta();
        Cuartetos.unirCuartetos(cuartetosRetorno, lugarAsignacion.generarCuartetos(coleccion));
        String izquierda = Temporal.actualTemporal();
        
        List<String> etiquetas = new ArrayList();
        
        for (CaseInstr caso : casos) {
            etiquetas.add(Etiqueta.siguienteEtiqueta());
        }
        
        for (int i = 0; i < casos.size(); i++) {
            String derecha = null;
            switch(casos.get(i).getValor().getTipo()){
                case CONST.CARACTER:{
                    derecha = "\""+(String)casos.get(i).getValor().getValor()+"\"";
                    break;
                }
                case CONST.ENTERO:{
                    derecha = (String)casos.get(i).getValor().getValor();
                    break;
                }
                case CONST.FLOTANTE:{
                    derecha = (String)casos.get(i).getValor().getValor();
                    break;
                }
            }
            
            String etiquetaDentro = Etiqueta.siguienteEtiqueta();
            cuartetosRetorno.add(new Cuarteto("==",izquierda, derecha, etiquetaDentro));
            cuartetosRetorno.add(((i+1)<casos.size())? 
                    new Cuarteto("goto",null,null,etiquetas.get(i+1)) 
                    : (etiquetaDefault!=null)? new Cuarteto("goto",null,null,etiquetaDefault) : new Cuarteto("goto",null,null,etiquetaFinal));
            cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaDentro));
            
            Codigo3Direcciones generador = new Codigo3Direcciones();
            Cuartetos.unirCuartetos(cuartetosRetorno, generador.generarCodigo3Direcciones(casos.get(i).getInstrucciones(), coleccion));
            cuartetosRetorno.add(new Cuarteto("goto",null,null,etiquetaFinal));
            if((i+1)<casos.size())cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetas.get(i+1)));
        }
        if(etiquetaDefault!=null){
            cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaDefault));
            coleccion.getSimbolos().agregarAmbitoTemporal();
            Codigo3Direcciones generador = new Codigo3Direcciones();
            Cuartetos.unirCuartetos(cuartetosRetorno, generador.generarCodigo3Direcciones(porDefecto.getInstrucciones(), coleccion));
            coleccion.getSimbolos().eliminarAmbitoTemporal();
        }
        cuartetosRetorno.add(new Cuarteto("etiqueta",null,null,etiquetaFinal));
        
        return cuartetosRetorno;
    }

    @Override
    public void analizarSemanticamente(Coleccion coleccion) {
        Dato dato = lugarAsignacion.analizarSemanticamente(coleccion);
        for (CaseInstr caso : casos) {
            if(dato!=null && !dato.getTipo().equals(caso.getValor().getTipo())) coleccion.getErrores().agregarError("Semantico", (String) caso.getValor().getValor(),"Un valor declarado en la instruccion case no coincide con el tipo de la variable a evaluar. ", caso.getPosicion());
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
    
}
