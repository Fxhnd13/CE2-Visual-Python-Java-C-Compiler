/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.sintactico;

import com.GUI.Mensajes;
import com.analisis.Lexer;
import com.analisis.Parser;
import com.analisis.objetos.analisis.ErrorManager;
import com.analisis.objetos.estructuras.Coleccion;
import com.analisis.objetos.estructuras.ColeccionInstr;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase destinada a la creacion del AST mediante el parser
 * @author jose_
 */
public class GeneradorAst {
    
    private ColeccionInstr instrucciones;
    private ErrorManager errores;
    
    public GeneradorAst(String codigo){
        //se generan las instrucciones
        generarAst(codigo);
        //se generan las estructuras
    }
    
    public void generarAst(String codigo){
        Lexer lexer = new Lexer(new StringReader(codigo));
        Parser parser = new Parser(lexer);
        try {
            parser.debug_parse();
            Mensajes mensajes = new Mensajes();
            mensajes.informacion("Se ha finalizado el analisis sintactico sin errores graves.");
            instrucciones = parser.getColeccionInstr();
            errores = parser.getErrores();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ColeccionInstr getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ColeccionInstr instrucciones) {
        this.instrucciones = instrucciones;
    }

    public ErrorManager getErrores() {
        return errores;
    }

    public void setErrores(ErrorManager errores) {
        this.errores = errores;
    }
    
}
