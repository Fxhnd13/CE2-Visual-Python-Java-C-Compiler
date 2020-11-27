/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI.Backend;

import com.GUI.Mensajes;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jose_
 */
public class ArchivosManager {
    
    private Mensajes mensajes = new Mensajes();
    
    /**
     * Metodo que permite al usuario seleccionar un fichero
     * @return el archivo guardado
     */
    public File cargarArchivo() {
        File file = null;
        
        JFileChooser filechooser = new JFileChooser();
        int resultado = filechooser.showOpenDialog(null);
        if(resultado == JFileChooser.APPROVE_OPTION){
            file = filechooser.getSelectedFile();
        }
        
        return file;
    }
    
    /**
     * Metodo que permite guardar el contenido de un archivo en su lugar de origen
     * @param documento a guardar
     */
    public void guardarArchivo(Documento documento){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(documento.getOrigen());
            pw = new PrintWriter(fichero);

            pw.print(documento.getContenido());
            documento.setModificado(false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    /**
     * Metodo que permite guardar el texto, en un archivo nuevo, sin modificar el original
     * @param documento a guardar
     */
    public void guardarComo(Documento documento){
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = filechooser.showOpenDialog(null);
        if(resultado == JFileChooser.APPROVE_OPTION){
            File temp = filechooser.getSelectedFile();
            if(temp != null){
                String nombre = JOptionPane.showInputDialog("Por favor ingrese el nombre del archivo:\n");
                if(nombre == null || nombre.isEmpty()){
                    mensajes.error("No se ingreso un nombre valido para el archivo, no se guardaron los cambios.");
                }else{
                    File file = new File(temp+"/"+nombre+".mlg");
                    documento.setOrigen(file);
                    guardarArchivo(documento);
                }
            }
        }
    }
    
}
