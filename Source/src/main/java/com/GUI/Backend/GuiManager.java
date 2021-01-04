/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI.Backend;

import com.GUI.Mensajes;
import com.analisis.objetos.analisis.ErrorA;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import com.analisis.semantico.General;
import com.analisis.sintactico.GeneradorAst;
import com.generadores.Codigo3Direcciones;
import com.generadores.EstructurasIntermedias;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Cuartetos;
import com.generadores.objetos.Etiqueta;
import com.generadores.objetos.Temporal;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private Mensajes mensajes;
    
    public GuiManager(Documento documentoActivo){
        this.documentoActivo = documentoActivo;
        this.manager = new ArchivosManager();
        this.mensajes = new Mensajes();
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

    /**
     * Metodo para guardar el codigo generado para el ejecutable de C
     * @param Codigo es el codigo que se escribirá en el archivo, que posteriormente se compilara y ejecutará
     */
    public void guardarYEjecutar(String codigo) {
        File tmp = new File("Generados");
        if(!tmp.isDirectory()) tmp.mkdirs();
        File file = new File("Generados/codigoC.c");
        manager.guardarArchivo(new Documento(file,false,codigo));
        try{
            Runtime.getRuntime().exec(new String[]{"xdg-open","Generados/"});
            //Runtime.getRuntime().exec(new String[]{"./Generados/generarC.sh" , "Generados/codigoC"});
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el ejecutable del codigo 3 direcciones generado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo para ejecutar el codigo generado 
     */
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

    /**
     * Metodo para generar los cuartetos y el codigo ejecutable en 3 direcciones
     * @param codigoFuente es el codigo que se desea transformar
     * @param erroresTextArea es el area en el que se desplegarán los errores (si hay)
     * @param codigoGenerado es el area en el que se desplegará el codigo generado
     */
    public List<Cuarteto> generarCodigo3D(JTextArea codigoFuente, JTextArea erroresTextArea, JTextArea codigoGenerado) {
        reiniciar(); //conteo en 0 de temporales y etiquetas
        GeneradorAst generadorAst = new GeneradorAst(codigoFuente.getText()); //generamos el ast a partir del archivo de entrada
        General analizadorSemantico = new General();
        analizadorSemantico.analizar(generadorAst); //hacemos el analisis semantico con la informacion recolectada 
        
        if(analizadorSemantico.getColeccion().getErrores().getErrores().isEmpty()){
            
            Codigo3Direcciones generador = new Codigo3Direcciones();
            List<Cuarteto> cuartetos = generador.generarCodigo(generadorAst.getInstrucciones(), analizadorSemantico.getColeccion());
            Cuartetos.eliminarRedundanciaEtiquetas(cuartetos);
            codigoGenerado.setText(Cuartetos.escribirCodigo3DireccionesNormal(cuartetos));
            mensajes.informacion("Se ha generado el codigo 3 direcciones exitosamente.");
            return cuartetos;
            
        }else{
            
            String errores = "";
            for (ErrorA error : analizadorSemantico.getColeccion().getErrores().getErrores()) {
                errores+=error.toString()+"\n";
            }
            mensajes.error("Se han encontrado "+analizadorSemantico.getColeccion().getErrores().getErrores().size()+" errores.");
            erroresTextArea.setText(errores);
            
        }
        return new ArrayList();
    }
    
    public void reiniciar(){
        Temporal.reiniciar();
        Etiqueta.reiniciar();
    }

    public void ejecutarCodigo3D(JTextArea codigoFuente, JTextArea erroresTextArea, JTextArea codigo3D) {
        List<Cuarteto> cuartetos = generarCodigo3D(codigoFuente, erroresTextArea, codigo3D);
        if(!cuartetos.isEmpty()){
            String codigo = Cuartetos.escribirCodigo3DireccionesEjecutable(cuartetos);
            mensajes.informacion("Se ha generado el codigo 3 direcciones ejecutable exitosamente.");
            guardarYEjecutar(codigo);
        }
    }
}
