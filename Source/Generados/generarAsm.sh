#!/bin/bash

echo "Compilando y enlazando..."

gcc codigoC.s -lm -o codigoAsm

echo "Ejecutando..."
echo "-----------------------------------------"

./codigoAsm