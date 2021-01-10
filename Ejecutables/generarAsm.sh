#!/bin/bash

echo "Compilando y enlazando..."

gcc "../Source/Generados/codigoC.s" -lm -o "../Source/Generados/codigoAsm"

echo "Ejecutando..."
echo "-----------------------------------------"

./../Source/Generados/codigoAsm