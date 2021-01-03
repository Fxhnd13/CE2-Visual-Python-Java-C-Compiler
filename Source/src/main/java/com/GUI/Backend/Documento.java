/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI.Backend;

import java.io.File;

/**
 * Clase destinada al almacenamiento de la informacion de un archivo abierto.
 * @author jose_
 */
public class Documento {
    
    private File origen;
    private boolean modificado;
    private String contenido;

    public Documento(File origen, boolean modificado, String contenido) {
        this.origen = origen;
        this.modificado = modificado;
        this.contenido = contenido;
    }
    
    public File getOrigen() {
        return origen;
    }

    public void setOrigen(File origen) {
        this.origen = origen;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
}
