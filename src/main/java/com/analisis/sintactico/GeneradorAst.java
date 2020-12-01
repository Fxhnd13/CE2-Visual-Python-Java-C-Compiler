/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analisis.sintactico;

import com.analisis.Lexer;
import com.analisis.Parser;
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
    
    public ColeccionInstr generarAst(String codigo){
        ColeccionInstr instrucciones = new ColeccionInstr();
        Lexer lexer = new Lexer(new StringReader(codigo));
        while(lexer.isAnalizando()){
            try {
                lexer.next_token();
            } catch (IOException ex) {
                Logger.getLogger(GeneradorAst.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        Parser parser = new Parser(lexer);
//        try {
//            parser.debug_parse();
//            System.out.println("*****************************************");
//            System.out.println("SE ANALIZO EXITOSAMENTE");
//            System.out.println("*****************************************");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return instrucciones;
    }
    
}
