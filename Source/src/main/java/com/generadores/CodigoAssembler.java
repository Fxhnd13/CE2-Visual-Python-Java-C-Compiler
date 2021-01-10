/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generadores;

import com.analisis.objetos.analisis.CONST;
import com.generadores.objetos.Cuarteto;
import com.generadores.objetos.Temporal;
import com.generadores.objetos.VarT;

/**
 * Clase destinada a la generacion de codigo assembler para los cuartetos
 * @author jose_
 */
public class CodigoAssembler {
    
    /**
     * Genera el codigo assembler para el cuarteto de una suma
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeSuma(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), 
                varD = Temporal.getTemporal(cuarteto.getDer()),
                resul = Temporal.getTemporal(cuarteto.getRes());
        switch(resul.getTipo()){
            case CONST.FLOTANTE:{
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        switch(varD.getTipo()){
                            case CONST.FLOTANTE:{
                                /*flotante = flotante + flotante
                                    movss	izquierda(%rip), %xmm1
                                    movss	derecha(%rip), %xmm0
                                    addss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getDer()+"(%rip), %xmm0\n" +
                                    "\taddss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.ENTERO:{
                                /*flotante = flotante + entero
                                    movl	derecha(%rip), %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    movss	izquierda(%rip), %xmm0
                                    addss	%xmm1, %xmm0
                                    movss	%xmm0, resultados(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\taddss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, r"+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*flotante = flotante + caracter
                                    movzbl	derecha(%rip), %eax
                                    movsbl	%al, %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    movss	izquierda(%rip), %xmm0
                                    addss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\taddss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.ENTERO:{
                        //flotante = entero + flotante
                        break;
                    }
                    case CONST.CARACTER:{
                        //flotante = caracter + flotante
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                if(varI==null)varI = new VarT(CONST.CTE, null);
                if(varD==null)varD = new VarT(CONST.CTE, null);
                switch(varI.getTipo()){
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*entero = entero + entero
                                    movl	izquierda(%rip), %edx
                                    movl	derecha(%rip), %eax
                                    addl	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %edx\n" +
                                "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                "\taddl\t%edx, %eax\n" +
                                "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*entero = entero + caracter
                                    movzbl	derecha(%rip), %eax
                                    movsbl	%al, %edx
                                    movl	izquierda(%rip), %eax
                                    addl	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %edx\n" +
                                    "\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\taddl\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CTE:{
                                /*entero = entero + cte
                                    	movl	izquierdo(%rip), %eax
                                        addl	$entero, %eax
                                        movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\taddl\t$"+cuarteto.getDer()+", %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        /*entero = caracter + entero
                            -----------------------------------------------------------------------------------------
                        */
                        codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                            "\tmovsbl\t%al, %edx\n" +
                            "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\taddl\t%edx, %eax\n" +
                            "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                        break;
                    }
                    case CONST.CTE:{
                        /*entero = cte + entero
                            	movl	derecho(%rip), %eax
                                addl	$entero, %eax
                                movl	%eax, resultado(%rip)
                        */
                            codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                "\taddl\t$"+cuarteto.getIz()+", %eax\n" +
                                "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                /*caracter = caracter + caracter
                    movzbl	izquierdo(%rip), %eax
                    movl	%eax, %edx
                    movzbl	derecho(%rip), %eax
                    addl	%edx, %eax
                    movb	%al, resultado(%rip)
                */
                codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
"                movl\t%eax, %edx\n" +
"                movzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
"                addl\t%edx, %eax\n" +
"                movb\t%al, "+cuarteto.getRes()+"(%rip)\n";
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Genera el codigo assembler para una resta
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeResta(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), 
                varD = Temporal.getTemporal(cuarteto.getDer()),
                resul = Temporal.getTemporal(cuarteto.getRes());
        switch(resul.getTipo()){
            case CONST.FLOTANTE:{
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        switch(varD.getTipo()){
                            case CONST.FLOTANTE:{
                                /*flotante = flotante + flotante
                                    	movss	izquierdo(%rip), %xmm0
                                        movss	derecho(%rip), %xmm1
                                        subss	%xmm1, %xmm0
                                        movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovss\t"+cuarteto.getDer()+"(%rip), %xmm1\n" +
                                    "\tsubss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.ENTERO:{
                                /*flotante = flotante + entero
                                    movss	izquierdo(%rip), %xmm0
                                    movl	derecho(%rip), %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    subss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="movss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tsubss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, resultado(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*flotante = flotante + caracter
                                    movss	izquierdo(%rip), %xmm0
                                    movzbl	derecho(%rip), %eax
                                    movsbl	%al, %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    subss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tsubss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.ENTERO:{
                        //flotante = entero + flotante
                        break;
                    }
                    case CONST.CARACTER:{
                        //flotante = caracter + flotante
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                if(varI==null)varI = new VarT(CONST.CTE, null);
                if(varD==null)varD = new VarT(CONST.CTE, null);
                switch(varI.getTipo()){
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*entero = entero + entero
                                    movl	izquierdo(%rip), %eax
                                    movl	derecho(%rip), %edx
                                    subl	%edx, %eax
                                    movl	%eax, resultado(%rip)  
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\tmovl\t"+cuarteto.getDer()+"(%rip), %edx\n" +
                                    "\tsubl\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*entero = entero + caracter
                                    movl	izquierdo(%rip), %eax
                                    movzbl	derecho(%rip), %edx
                                    movsbl	%dl, %edx
                                    subl	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %edx\n" +
                                    "\tmovsbl\t%dl, %edx\n" +
                                    "\tsubl\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CTE:{
                                //entero = entero + cte
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        //entero = caracter + entero
                        break;
                    }
                    case CONST.CTE:{
                        //entero = cte + entero
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                /*caracter = caracter + caracter
                    vmovzbl	izquierdo(%rip), %eax
                    movl	%eax, %edx
                    movzbl	derecho(%rip), %eax
                    movl	%eax, %ecx
                    movl	%edx, %eax
                    subl	%ecx, %eax
                    movb	%al, resultado(%rip)
                */
                codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                    "\tmovl\t%eax, %edx\n" +
                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                    "\tmovl\t%eax, %ecx\n" +
                    "\tmovl\t%edx, %eax\n" +
                    "\tsubl\t%ecx, %eax\n" +
                    "\tmovb\t%al, "+cuarteto.getRes()+"(%rip)\n";
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Genera el codigo assembler de una multiplicacion
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeMultiplicacion(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), 
                varD = Temporal.getTemporal(cuarteto.getDer()),
                resul = Temporal.getTemporal(cuarteto.getRes());
        switch(resul.getTipo()){
            case CONST.FLOTANTE:{
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        switch(varD.getTipo()){
                            case CONST.FLOTANTE:{
                                /*flotante = flotante + flotante
                                    movss	izquierdo(%rip), %xmm1
                                    movss	derecho(%rip), %xmm0
                                    mulss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getDer()+"(%rip), %xmm0\n" +
                                    "\tmulss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.ENTERO:{
                                /*flotante = flotante + entero
                                    movl	derecho(%rip), %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    movss	izquierdo(%rip), %xmm0
                                    mulss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmulss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*flotante = flotante + caracter
                                    movzbl	derecho(%rip), %eax
                                    movsbl	%al, %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    movss	izquierdo(%rip), %xmm0
                                    mulss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmulss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.ENTERO:{
                        //flotante = entero + flotante
                        break;
                    }
                    case CONST.CARACTER:{
                        //flotante = caracter + flotante
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                if(varI==null)varI = new VarT(CONST.CTE, null);
                if(varD==null)varD = new VarT(CONST.CTE, null);
                switch(varI.getTipo()){
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*entero = entero + entero
                                    	movl    izquierdo(%rip), %edx
                                        movl	derecho(%rip), %eax
                                        imull	%edx, %eax
                                        movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %edx\n" +
                                    "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\timull\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*entero = entero + caracter
                                    movzbl	derecho(%rip), %eax
                                    movsbl	%al, %edx
                                    movl	izquierdo(%rip), %eax
                                    imull	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %edx\n" +
                                    "\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\timull\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CTE:{
                                //entero = entero + cte
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        //entero = caracter + entero
                        break;
                    }
                    case CONST.CTE:{
                        //entero = cte + entero
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                /*caracter = caracter + caracter
                    movzbl	izquierdo(%rip), %eax
                    movl	%eax, %ecx
                    movzbl	derecho(%rip), %eax
                    movl	%eax, %edx
                    movl	%ecx, %eax
                    imull	%edx, %eax
                    movb	%al, resultado(%rip)
                */
                codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                    "\tmovl\t%eax, %ecx\n" +
                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                    "\tmovl\t%eax, %edx\n" +
                    "\tmovl\t%ecx, %eax\n" +
                    "\timull\t%edx, %eax\n" +
                    "\tmovb\t%al, "+cuarteto.getRes()+"(%rip)\n";
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Genera el codigo assembler para una division
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeDivision(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), 
                varD = Temporal.getTemporal(cuarteto.getDer()),
                resul = Temporal.getTemporal(cuarteto.getRes());
        switch(resul.getTipo()){
            case CONST.FLOTANTE:{
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        switch(varD.getTipo()){
                            case CONST.FLOTANTE:{
                                /*flotante = flotante + flotante
                                    movss	izquierdo(%rip), %xmm0
                                    movss	derecho(%rip), %xmm1
                                    divss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovss\t"+cuarteto.getDer()+"(%rip), %xmm1\n" +
                                    "\tdivss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.ENTERO:{
                                /*flotante = flotante + entero
                                    movss	izquierdo(%rip), %xmm0
                                    movl	derecho(%rip), %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    divss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tdivss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*flotante = flotante + caracter
                                    movss	izquierdo(%rip), %xmm0
                                    movzbl	derecho(%rip), %eax
                                    movsbl	%al, %eax
                                    pxor	%xmm1, %xmm1
                                    cvtsi2ssl	%eax, %xmm1
                                    divss	%xmm1, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                    "\tmovsbl\t%al, %eax\n" +
                                    "\tpxor\t%xmm1, %xmm1\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm1\n" +
                                    "\tdivss\t%xmm1, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*flotante = entero / entero
                                    movl	izquierdo(%rip), %eax
                                    movl	derecho(%rip), %esi
                                    cltd
                                    idivl	%esi
                                    pxor	%xmm0, %xmm0
                                    cvtsi2ssl	%eax, %xmm0
                                    movss	%xmm0, resultado(%rip)
                                */
                                    codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                        "\tmovl\t"+cuarteto.getDer()+"(%rip), %esi\n" +
                                        "\tcltd\n" +
                                        "\tidivl\t%esi\n" +
                                        "\tpxor\t%xmm0, %xmm0\n" +
                                        "\tcvtsi2ssl\t%eax, %xmm0\n" +
                                        "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*flotante = entero / caracter
                                        movl	izquierdo(%rip), %eax
                                        movzbl	derecho(%rip), %edx
                                        movsbl	%dl, %esi
                                        cltd
                                        idivl	%esi
                                        pxor	%xmm0, %xmm0
                                        cvtsi2ssl	%eax, %xmm0
                                        movss	%xmm0, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %edx\n" +
                                    "\tmovsbl\t%dl, %esi\n" +
                                    "\tcltd\n" +
                                    "\tidivl\t%esi\n" +
                                    "\tpxor\t%xmm0, %xmm0\n" +
                                    "\tcvtsi2ssl\t%eax, %xmm0\n" +
                                    "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        switch(varD.getTipo()){
                            //flotante = caracter + flotante
                            case CONST.FLOTANTE:{
                                break;
                            }
                            //flotante = caracter  caracter
                            case CONST.CARACTER:{
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                if(varI==null)varI = new VarT(CONST.CTE, null);
                if(varD==null)varD = new VarT(CONST.CTE, null);
                switch(varI.getTipo()){
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                //entero = entero + entero
                                break;
                            }
                            case CONST.CARACTER:{
                                //entero = entero + caracter
                                break;
                            }
                            case CONST.CTE:{
                                //entero = entero + cte
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        //entero = caracter + entero
                        break;
                    }
                    case CONST.CTE:{
                        //entero = cte + entero
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                //caracter = caracter + caracter
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Generacion de codigo assembler para una operacion de modulo
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeModulo(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), 
                varD = Temporal.getTemporal(cuarteto.getDer()),
                resul = Temporal.getTemporal(cuarteto.getRes());
        switch(resul.getTipo()){
            case CONST.FLOTANTE:{
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        switch(varD.getTipo()){
                            case CONST.FLOTANTE:{
                                //flotante = flotante + flotante
                                break;
                            }
                            case CONST.ENTERO:{
                                //flotante = flotante + entero
                                break;
                            }
                            case CONST.CARACTER:{
                                //flotante = flotante + caracter
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.ENTERO:{
                        //flotante = entero + flotante
                        break;
                    }
                    case CONST.CARACTER:{
                        //flotante = caracter + flotante
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                if(varI==null)varI = new VarT(CONST.CTE, null);
                if(varD==null)varD = new VarT(CONST.CTE, null);
                switch(varI.getTipo()){
                    case CONST.ENTERO:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*entero = entero + entero
                                    movl	izquierdo(%rip), %eax
                                    movl	derecho(%rip), %ecx
                                    cltd
                                    idivl	%ecx
                                    movl	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\tmovl\t"+cuarteto.getDer()+"(%rip), %ecx\n" +
                                    "\tcltd\n" +
                                    "\tidivl\t%ecx\n" +
                                    "\tmovl\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CARACTER:{
                                /*entero = entero + caracter
                                    movl	izquierdo(%rip), %eax
                                    movzbl	derecho(%rip), %edx
                                    movsbl	%dl, %ecx
                                    cltd
                                    idivl	%ecx
                                    movl	%edx, %eax
                                    movl	%eax, resultado(%rip)
                                */
                                codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                    "\tmovzbl\t"+cuarteto.getDer()+"(%rip), %edx\n" +
                                    "\tmovsbl\t%dl, %ecx\n" +
                                    "\tcltd\n" +
                                    "\tidivl\t%ecx\n" +
                                    "\tmovl\t%edx, %eax\n" +
                                    "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                break;
                            }
                            case CONST.CTE:{
                                //entero = entero + cte
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CARACTER:{
                        switch(varD.getTipo()){
                            case CONST.ENTERO:{
                                /*entero = caracter % entero
                                
                                */
                                break;
                            }
                            case CONST.CARACTER:{
                                /*entero = entero % caracter
                                
                                */
                                break;
                            }
                        }
                        break;
                    }
                    case CONST.CTE:{
                        //entero = cte + entero
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                //caracter = caracter + caracter
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Generacion de codigo assembler para la estrutrua de un condicional
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeCondicional(Cuarteto cuarteto){
        String codigo = "";
        VarT varI = Temporal.getTemporal(cuarteto.getIz()), varD = Temporal.getTemporal(cuarteto.getDer());
        switch(varI.getTipo()){
            case CONST.FLOTANTE:{
                switch(varD.getTipo()){
                    case CONST.FLOTANTE:{
                        /* flotante op flotante
                            movss	izquierdo(%rip), %xmm1
                            movss	derecho(%rip), %xmm0
                            comiss	%xmm1, %xmm0
                        */
                        codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm1\n" +
                            "\tmovss\t"+cuarteto.getDer()+"(%rip), %xmm0\n" +
                            "\tcomiss\t%xmm1, %xmm0\n";
                        break;
                    }
                    case CONST.ENTERO:{
                        /*flotante op entero
                            movl	derecho(%rip), %eax
                            pxor	%xmm0, %xmm0
                            cvtsi2ssl	%eax, %xmm0
                            movss	izquierdo(%rip), %xmm1
                            comiss	%xmm1, %xmm0
                        */
                        codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\tpxor\t%xmm0, %xmm0\n" +
                            "\tcvtsi2ssl\t%eax, %xmm0\n" +
                            "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm1\n" +
                            "\tcomiss\t%xmm1, %xmm0\n";
                        break;
                    }
                }
                break;
            }
            case CONST.ENTERO:{
                switch(varD.getTipo()){
                    case CONST.FLOTANTE:{
                        /* entero op flotante
                            movl	derecho(%rip), %eax
                            pxor	%xmm0, %xmm0
                            cvtsi2ssl	%eax, %xmm0
                            movss	izquierdo(%rip), %xmm1
                            comiss	%xmm1, %xmm0
                        */
                        codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\tpxor\t%xmm1, %xmm1\n" +
                            "\tcvtsi2ssl\t%eax, %xmm1\n" +
                            "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                            "\tcomiss\t%xmm1, %xmm0\n";
                        break;
                    }
                    case CONST.ENTERO:{
                        /*Entero op entero
                            movl	izquierdo(%rip), %edx
                            movl	derecho(%rip), %eax
                            cmpl	%eax, %edx
                        */
                        codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %edx\n" +
                            "\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\tcmpl\t%eax, %edx\n";
                        break;
                    }
                }
                break;
            }
            case CONST.CARACTER:{
                /* caracter op caracter
                    movzbl	izquierdo(%rip), %edx
                    movzbl	derecho(%rip), %eax
                    cmpb	%al, %dl
                */
                codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %edx\n" +
                    "\tmovzbl\tt"+cuarteto.getDer()+"(%rip), %eax\n" +
                    "\tcmpb\t%al, %dl\n";
                break;
            }
        }
        switch(cuarteto.getOp()){
            case ">":{ //verificado
                codigo+="\tjg\t."+cuarteto.getRes()+"\n"; break;
            }
            case ">=":{
                codigo+="\tjge\t."+cuarteto.getRes()+"\n"; break;
            }
            case "<":{
                codigo+="\tjl\t."+cuarteto.getRes()+"\n"; break;
            }
            case "<=":{
                codigo+="\tjle\t."+cuarteto.getRes()+"\n"; break;
            }
            case "==":{
                codigo+="\tje\t."+cuarteto.getRes()+"\n"; break;
            }
            case "!=":{
                codigo+="\tjne\t."+cuarteto.getRes()+"\n"; break;
            }
        }
        return codigo;
    }
    
    /**
     * Generacion de codigo assembler para una asignacion
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeAsignacion(Cuarteto cuarteto){
        String codigo = "";
        switch(cuarteto.getOp()){
            case ":=":{
                VarT valorAsignar = Temporal.getTemporal(cuarteto.getIz());
                if(valorAsignar==null){
                    valorAsignar = Temporal.getTemporal(cuarteto.getRes());
                    switch(valorAsignar.getTipo()){
                        case CONST.FLOTANTE:{
                            codigo+="\tmovss\t."+cuarteto.getIz()+"(%rip), %xmm0\n"
                                +"\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                            break;
                        }
                        case CONST.ENTERO:{
                            codigo+="\tmovl\t$"+cuarteto.getIz()+", "+cuarteto.getRes()+"(%rip)\n";
                            break;
                        }
                        case CONST.CARACTER:{
                            codigo+="\tmovb\t$"+((int)cuarteto.getIz().toCharArray()[1])+", "+cuarteto.getRes()+"(%rip)\n";
                            break;
                        }
                    }
                }else{
                    switch(Temporal.getTemporal(cuarteto.getRes()).getTipo()){
                        case CONST.FLOTANTE:{
                            switch(valorAsignar.getTipo()){
                                case CONST.FLOTANTE:{
                                    codigo+="\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                                        "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                    break;
                                }
                                case CONST.ENTERO:{
                                    codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                        "\tpxor\t%xmm0, %xmm0\n" +
                                        "\tcvtsi2ssl\t%eax, %xmm0\n" +
                                        "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                    break;
                                }
                                case CONST.CARACTER:{
                                    codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                        "\tmovsbl\t%al, %eax\n" +
                                        "\tpxor\t%xmm0, %xmm0\n" +
                                        "\tcvtsi2ssl\t%eax, %xmm0\n" +
                                        "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                                    break;
                                }
                            }
                            break;
                        }
                        case CONST.ENTERO:{
                            switch(valorAsignar.getTipo()){
                                case CONST.ENTERO:{
                                    codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                            "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                    break;
                                }
                                case CONST.CARACTER:{
                                    codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                        "\tmovsbl\t%al, %eax\n" +
                                        "\tmovl\t%eax, "+cuarteto.getRes()+"(%rip)\n";
                                    break;
                                }
                            }
                            break;
                        }
                        case CONST.CARACTER:{
                            codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %eax\n" +
                                "\tmovb\t%al, "+cuarteto.getRes()+"(%rip)";
                            break;
                        }
                    }
                }
                break;
            }
            case ":=a":{
                VarT varI = Temporal.getTemporal(cuarteto.getIz());
                switch(varI.getTipo()){
                    case CONST.FLOTANTE:{
                        codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\tmovss\t"+cuarteto.getIz()+"(%rip), %xmm0\n" +
                            "\tcltq\n" +
                            "\tleaq\t0(,%rax,4), %rdx\n" +
                            "\tleaq\t"+cuarteto.getRes()+"(%rip), %rax\n" +
                            "\tmovss\t%xmm0, (%rdx,%rax)\n";
                        break;
                    }
                    case CONST.ENTERO:{
                        codigo+="\tmovl\t"+cuarteto.getIz()+"(%rip), %edx\n"
                            +"\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n"
                            +"\tpxor\t%xmm0, %xmm0\n"
                            +"\tcvtsi2ssl\t%edx, %xmm0\n"
                            +"\tcltq\n"
                            +"\tleaq\t0(,%rax,4), %rdx\n"
                            +"\tleaq\t"+cuarteto.getRes()+"(%rip), %rax\n"
                            +"\tmovss\t%xmm0, (%rdx,%rax)\n";
                        break;
                    }
                    case CONST.CARACTER:{
                        codigo+="\tmovzbl\t"+cuarteto.getIz()+"(%rip), %edx\n"
                            +"\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n"
                            +"\tmovsbl\t%dl, %edx\n"
                            +"\tpxor\t%xmm0, %xmm0\n"
                            +"\tcvtsi2ssl\t%edx, %xmm0\n"
                            +"\tcltq\n"
                            +"\tleaq\t0(,%rax,4), %rdx\n"
                            +"\tleaq\t"+cuarteto.getRes()+"(%rip), %rax\n"
                            +"\tmovss\t%xmm0, (%rdx,%rax)\n";
                        break;
                    }
                }
                break;
            }
            case "arreglo":{
                VarT valorAsignar = Temporal.getTemporal(cuarteto.getRes());
                switch(valorAsignar.getTipo()){
                        case CONST.FLOTANTE:{
                            codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                "\tcltq\n" +
                                "\tleaq\t0(,%rax,4), %rdx\n" +
                                "\tleaq\t"+cuarteto.getIz()+"(%rip), %rax\n" +
                                "\tmovss\t(%rdx,%rax), %xmm0\n" +
                                "\tmovss\t%xmm0, "+cuarteto.getRes()+"(%rip)\n";
                            break;
                        }
                        case CONST.ENTERO:{
                            codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                            "\tcltq\n" +
                            "\tleaq\t0(,%rax,4), %rdx\n" +
                            "\tleaq\t"+cuarteto.getIz()+"(%rip), %rax\n" +
                            "\tmovss\t(%rdx,%rax), %xmm0\n" +
                            "\tcvttss2sil\t%xmm0, %eax\n" +
                            "\tmovl\t%eax, "+cuarteto.getRes()+"%rip)\n";
                            break;
                        }
                        case CONST.CARACTER:{
                            codigo+="\tmovl\t"+cuarteto.getDer()+"(%rip), %eax\n" +
                                "\tcltq\n" +
                                "\tleaq\t0(,%rax,4), %rdx\n" +
                                "\tleaq\t"+cuarteto.getIz()+"(%rip), %rax\n" +
                                "\tmovss\t(%rdx,%rax), %xmm0\n" +
                                "\tcvttss2sil\t%xmm0, %eax\n" +
                                "\tmovb\t%al, "+cuarteto.getRes()+"(%rip)\n";
                            break;
                        }
                }
                break;
            }
        }
        return codigo;
    }
    
    /**
     * Generacion de codigo assembler para una instruccion scan
     * @param cuarteto
     * @return
     */
    public String generarCodigoDeScanf(Cuarteto cuarteto){
        String codigo = "";
        String etiqueta = "";
        switch(cuarteto.getIz()){
            case " %c": etiqueta = "LC1"; break;
            case "%c": etiqueta = "LC2"; break;
            case "%d": etiqueta = "LC3"; break;
            case "%f": etiqueta = "LC4"; break;
        }
        codigo+="\tleaq\t"+cuarteto.getRes()+"(%rip), %rsi\n"
                + "\tleaq\t."+etiqueta+"(%rip), %rdi\n"
                + "\tmovl\t$0, %eax\n"
                + "\tcall\t__isoc99_scanf@PLT\n";
        return codigo;
    }
}
