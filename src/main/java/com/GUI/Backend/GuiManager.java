/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI.Backend;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * Clase destinada al manejo de los metodos realizados de la interfaz y no cargar de codigo la interfaz
 * @author jose_
 */
public class GuiManager {
    
    private Documento documentoActivo;
    private ArchivosManager manager;
    
    public GuiManager(Documento documentoActivo){
        this.documentoActivo = documentoActivo;
        this.manager = new ArchivosManager();
    }

    public Documento getDocumentoActivo() {
        return documentoActivo;
    }

    public void setDocumentoActivo(Documento documentoActivo) {
        this.documentoActivo = documentoActivo;
    }
    
    /**
     * Lee un fichero y lo carga en el text area para mostrarlo al usuario
     * @param codigoFuenteTextArea text area, donde se mostrará el codigo cargado
     */
    public void abrir(JTextArea codigoFuenteTextArea) {
        File file = manager.cargarArchivo();
        if(file == null || file.getName().equals("")){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo seleccionado o no selecciono un arhivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                String cadena, contenido = "";
                FileReader f = new FileReader(file);
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!= null){
                    contenido += cadena+"\n";
                }
                contenido = contenido.substring(0,contenido.length()-1);
                this.documentoActivo = new Documento(file, false, contenido);
                codigoFuenteTextArea.setText(contenido);
            }catch(FileNotFoundException ex){
                JOptionPane.showMessageDialog(null, "No se encontro el archivo seleccionado o no selecciono un arhivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Ocurrio un error EditorManager/cargarTab", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Guarda los cambios realizados en el text area de codigo fuente
     * @param texto a guardar
     */
    public void guardar(String texto) {
        documentoActivo.setContenido(texto);
        documentoActivo.setModificado(false);
        if(documentoActivo.getOrigen() != null){
            manager.guardarArchivo(documentoActivo);
        }else{
            manager.guardarComo(documentoActivo);
        }
    }

    /**
     * Guarda los cambios realizados en el text area, pero sin modificar el archivo original
     * @param texto a guardar
     */
    public void guardarComo(String texto) {
        documentoActivo.setContenido(texto);
        documentoActivo.setModificado(false);
        manager.guardarComo(documentoActivo); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo que agrega las funcionalidades de cortar, copiar, pegar, undo y redo.
     * @param codigoFuenteTextArea text area al que se desea agregar estas funcionalidades
     */
    public void habilitarFuncionalidades(JTextArea codigoFuenteTextArea) {
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK);

        UndoManager undoManager = new UndoManager();

        Document document = codigoFuenteTextArea.getDocument();
        document.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        // Map undo action 
        codigoFuenteTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoKeyStroke, "undoKeyStroke");
        codigoFuenteTextArea.getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                } catch (CannotUndoException cue) {
                }
            }
        });
        // Map redo action 
        codigoFuenteTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoKeyStroke, "redoKeyStroke");
        codigoFuenteTextArea.getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {
                }
            }
        });
        
        ActionMap acciones = codigoFuenteTextArea.getActionMap();
        
        Action accionCopiar = acciones.get(DefaultEditorKit.copyAction);
        Action accionPegar = acciones.get(DefaultEditorKit.pasteAction);
        Action accionCortar = acciones.get(DefaultEditorKit.cutAction);
        
        accionCopiar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
        accionCortar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
        accionPegar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('V', InputEvent.CTRL_DOWN_MASK));
    }

    public void guardarEjecutable(String codigo) {
        File tmp = new File("Generados/Codigo");
        if(!tmp.isDirectory()) tmp.mkdirs();
        File file = new File("Generados/Codigo/codigoC.c");
        manager.guardarArchivo(new Documento(file,false,codigo));
        try{
            Runtime.getRuntime().exec(new String[]{"gcc","-o","Generados/ejecutableC", "Generados/Codigo/codigoC.c"});
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el ejecutable del codigo 3 direcciones generado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ejecutarCodigo3Direcciones(){
        File file = new File("Generados/ejecutableC");
        if(file.exists()){
            try {
                Runtime.getRuntime().exec(new String[]{"xdg-open","Generados/ejecutableC"});
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar el codigo 3 direcciones generado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se encontró un archivo ejecutable compile e intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
