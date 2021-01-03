#include <stdio.h>

float stack[10000];
float heap[10000];
int p = 0;
int h = 0;
int t00;
int t0;
float t1;
int t2;
int t3;
float t4;
int t5;
int t6;
int t7;
int t8;
int t9;
int t10;
float t11;
int t12;
float t13;
float t14;
int t15;
int t16;
float t17;
int t18;
float t19;
int t20;
int t21;
float t22;
int t23;
int t24;
float t25;
int t26;
int t27;
float t28;
int t29;
int t30;
float t31;
int t32;
float t33;
int t34;
float t35;
int t36;
int t37;
float t38;
int t39;
int t40;
float t41;
int t42;
float t43;
int t44;
float t45;
float t46;
int t47;
float t48;
int t49;
int t50;
float t51;
int t52;
float t53;
int t54;
int t55;
float t56;
int t57;
int t58;
float t59;
int t60;
int t61;
int t62;
int t63;
int t64;
int t65;
int t66;
int t67;
int t68;
int t69;
int t70;
int t71;
int t72;
int t73;
int t74;
int t75;
int t76;
int t77;
int t78;
float t79;
int t80;
int t81;
float t82;
int t83;
int t84;
int t85;
float t86;
int t87;
float t88;
int t89;
float t90;
int t91;
int t92;
int t93;
int t94;
int t95;
int t96;
int t97;
int t98;
int t99;
int t100;
int t101;
float t102;
int t103;
float t104;
float t105;
int t106;
int t107;
float t108;
int t109;
float t110;
int t111;
int t112;
float t113;
int t114;
int t115;
float t116;
int t117;
float t118;
int t119;
float t120;

void VB_Saludo(void);
void VB_Factorial_ENTERO(void);
void JAVA_Algo_saludar(void);
void JAVA_Algo_hacerSuma_ENTERO_ENTERO(void);
void JAVA_Algo_suma_ENTERO_ENTERO(void);
void JAVA_Algo_Algo(void);
void PY_edadmeses_INDEFINIDO_INDEFINIDO(void);

void VB_Saludo(void){ 
printf("Segundo Proyecto de Compiladores 2");
}

void VB_Factorial_ENTERO(void){ 
t0 = p + 2;
t1 = (float)stack[t0];
t2 = p + 4;
stack[t2] = (float) t1;
t3 = p + 2;
t4 = (float)stack[t3];
t5 = 0;
if (t4 > t5) goto et2;
goto et3;
et2:
t6 = 1;
t7 = p + 1;
stack[t7] = (float) t6;
goto et1;
et3:
t8 = 1;
t9 = p + 3;
stack[t9] = (float) t8;
et4:
t10 = p + 3;
t11 = (float)stack[t10];
t12 = p + 2;
t13 = (float)stack[t12];
t14 = t11 * t13;
t15 = p + 3;
stack[t15] = (float) t14;
t16 = p + 2;
t17 = (float)stack[t16];
t18 = 1;
t19 = t17 - t18;
t20 = p + 2;
stack[t20] = (float) t19;
t21 = p + 2;
t22 = (float)stack[t21];
t23 = 1;
if (t22 > t23) goto et4;
goto et5;
et5:
t24 = p + 3;
t25 = (float)stack[t24];
t26 = p + 1;
stack[t26] = (float) t25;
et1:
t27 = p + 1;
t28 = (float)stack[t27];
t29 = p + 0;
stack[t29] = (float) t28;
goto et0;
et0:
t00 = 0;
}

void JAVA_Algo_saludar(void){ 
printf("hola mundo");
}

void JAVA_Algo_hacerSuma_ENTERO_ENTERO(void){ 
t30 = p + 0;
t31 = (float)stack[t30];
t32 = p + 1;
t33 = (float)stack[t32];
t34 = 0 + p;
t35 = (float)stack[t34];
p = p + 3;
t36 = 0 + p;
stack[t36] = (float) t35;
JAVA_Algo_suma_ENTERO_ENTERO();
t37 = 0 + p;
t38 = (float)stack[t37];
p = p - 3;
t39 = p + 2;
stack[t39] = (float) t38;
printf("El resultado es:");
t40 = p + 2;
t41 = (float)stack[t40];
printf("%f",(float)t41);
}

void JAVA_Algo_suma_ENTERO_ENTERO(void){ 
t42 = p + 2;
t43 = (float)stack[t42];
t44 = p + 3;
t45 = (float)stack[t44];
t46 = t43 + t45;
t47 = p + 0;
stack[t47] = (float) t46;
goto et6;
et6:
t00 = 0;
}

void JAVA_Algo_Algo(void){ 
}

void PY_edadmeses_INDEFINIDO_INDEFINIDO(void){ 
printf("Ingrese su edad:");
scanf("%f",&t48);
t49 = p + 1;
stack[t49] = (float) t48;
t50 = p + 1;
t51 = (float)stack[t50];
t52 = 12;
t53 = t51 * t52;
t54 = p + 2;
stack[t54] = (float) t53;
printf("meses = ");
t55 = p + 2;
t56 = (float)stack[t55];
printf("%f",(float)t56);
et7:
t00 = 0;
}


int main(void){
t57 = 10;
t58 = p + 3;
stack[t58] = (float) t57;
stack[(int)4] = (float) h;
h = 1 + h;
t59 = (float)stack[t58];
t60 = 0 + t58;
p = p + t60;
t61 = 0 + p;
stack[t61] = (float) t59;
JAVA_Algo_Algo();
p = 5 - p;
t62 = 1;
t63 = 4;
t64 = 0;
t64 = t62 * t64;
t64 = t63 * t64;
t65 = 0 + t58;
t66 = t64 + t65;
t67 = 1 + t66;
t68 = 1 + t67;
t69 = 0;
t70 = p + t68;
stack[t70] = (float) t69;
t71 = 1 + t68;
printf("Bienvenido");
t72 = 1 + t71;
p = p + t72;
VB_Saludo();
t73 = 1 + t71;
p = p - t73;
printf("Ingrese el primer valor entero");
scanf("%d",&t74);
t75 = p + t66;
stack[t75] = (float) t74;
printf("Ingrese el segudo valor entero");
scanf("%d",&t76);
t77 = p + t67;
stack[t77] = (float) t76;
t78 = p + t68;
t79 = (float)stack[t78];
t80 = 1 + t71;
p = p + t80;
VB_Factorial_ENTERO();
t81 = 0 + p;
t82 = (float)stack[t81];
t83 = 1 + t71;
p = p - t83;
t84 = p + t71;
stack[t84] = (float) t82;
printf("El factorial de es ");
t85 = p + t71;
t86 = (float)stack[t85];
printf("%f",(float)t86);
printf("Conversion de años a meses");
t87 = p + t66;
t88 = (float)stack[t87];
t89 = p + t67;
t90 = (float)stack[t89];
t91 = 1 + t71;
p = p + t91;
t92 = p + 1;
stack[t92] = (float) t90;
PY_edadmeses_INDEFINIDO_INDEFINIDO();
t93 = 1 + t71;
p = p - t93;
t94 = 1;
t95 = 1;
t96 = t94;
t97 = t96 * t62;
t98 = t97 + t95;
t99 = t98 + t65;
t100 = 0;
stack[t99] = (float) t100;
et10:
t112 = p + t68;
t113 = (float)stack[t112];
t114 = 10;
if (t113 < t114) goto et8;
goto et9;
et8:
t101 = p + t71;
t102 = (float)stack[t101];
t103 = p + t66;
t104 = (float)stack[t103];
t105 = t102 * t104;
t106 = p + t71;
stack[t106] = (float) t105;
t107 = p + t68;
t108 = (float)stack[t107];
t109 = 1;
t110 = t108 + t109;
t111 = p + t68;
stack[t111] = (float) t110;
goto et10;
et9:
t115 = p + t66;
t116 = (float)stack[t115];
printf("%f",(float)t116);
printf(" ");
printf("^ ");
t117 = p + t67;
t118 = (float)stack[t117];
printf("%f",(float)t118);
printf(" = ");
t119 = p + t71;
t120 = (float)stack[t119];
printf("%f",(float)t120);
getchar();
return 0;
}
