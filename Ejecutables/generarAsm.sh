#!/bin/bash

echo "Compilando y enlazando..."

gcc "../Source/Generados/codigoAsm.s" -lm -o "../Source/Generados/ejecutableAsm"

echo "Ejecutando..."
echo "-----------------------------------------"

./../Source/Generados/ejecutableAsm