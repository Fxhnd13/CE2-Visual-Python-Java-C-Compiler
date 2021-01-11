/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores.objetos;

import com.analisis.objetos.analisis.CONST;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose_
 */
public class Temporal {
    
    public static String temporal = "t";
    public static List<VarT> temporales = new ArrayList();
    public static int contador = 0;
    
    public static String siguienteTemporal(String tipo) {
        temporal = "t" + contador;
        contador++;
        temporales.add(new VarT(tipo,temporal));
        return temporal;
    } 
    
    public static String actualTemporal() {
        contador--;
        temporal = "t" + contador;
        contador++;
        return temporal;
    }
   
    public static String ultimoTemporal() {
        return temporal;
    }

    public static void reiniciar() {
        temporales.clear();
        contador = 0;
    }

    static String getTipoTemporal(String resultado) {
        for (VarT temporal : temporales) {
            if(temporal.getTemporal().equals(resultado)) return temporal.getTipo();
        }
        return null;
    }

    public static VarT getTemporal(String resultado) {
        for (VarT temporal : temporales) {
            if(temporal.getTemporal().equals(resultado)) return temporal;
        }
        return null;
    }

    public static void eliminarTemporalesIndefinidos(List<Cuarteto> cuartetos) {
        for (VarT temporal : temporales) {
            if(temporal.getTipo().equals(CONST.INDEFINIDO)) temporal.setTipo(CONST.FLOTANTE);
        }
    }
}
