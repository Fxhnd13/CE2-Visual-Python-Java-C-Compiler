/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.sintactico;

import com.analisis.Lexer;
import com.analisis.Parser;
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
    private Coleccion coleccion;
    
    public GeneradorAst(String codigo){
        //se generan las instrucciones
        generarAst(codigo);
        //se generan las estructuras
    }
    
    public void generarAst(String codigo){
        Lexer lexer = new Lexer(new StringReader(codigo));
//        while(lexer.isAnalizando()){
//            try {
//                lexer.next_token();
//            } catch (IOException ex) {
//                Logger.getLogger(GeneradorAst.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        
        //Aqui tenemos que generar las estructuras intermecias que rayos
        Parser parser = new Parser(lexer);
        try {
            parser.parse();
            System.out.println("*****************************************");
            System.out.println("SE ANALIZO EXITOSAMENTE");
            System.out.println("*****************************************");
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

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }
    
    
}
