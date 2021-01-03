/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI;

import javax.swing.JOptionPane;

/**
 * Clase destinada a la visualización de mensajes al usuario.
 * @author jose_
 */
public class Mensajes {
    
    /**
     * Metodo que muestra un mensaje informativo
     * @param mensaje mensaje a mostrar
     */
    public void informacion(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Metodo que muestra un mensaje de advertencia
     * @param mensaje mensaje a mostrar
     */
    public void advertencia(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Metodo que muestra un mensaje de error
     * @param mensaje mensaje a mostrar
     */
    public void error(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
