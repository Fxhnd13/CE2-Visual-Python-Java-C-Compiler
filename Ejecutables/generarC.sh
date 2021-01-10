#!/bin/bash
echo "------------------------------------------"
echo "Compilando y enlazando..."
echo "------------------------------------------"

gcc -o "../Source/Generados/codigoC" "../Source/Generados/codigoC.c";

echo "------------------------------------------"
echo "Ejecutando Codigo Generado"
echo "------------------------------------------"

./../Source/Generados/codigoC