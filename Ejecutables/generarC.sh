#!/bin/bash
echo "------------------------------------------"
echo "Compilando y enlazando..."
echo "------------------------------------------"

gcc -o "../Source/Generados/ejecutableC" "../Source/Generados/codigoC.c";

echo "------------------------------------------"
echo "Ejecutando Codigo Generado"
echo "------------------------------------------"

./../Source/Generados/ejecutableC