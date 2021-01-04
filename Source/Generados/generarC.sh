#!/bin/bash
echo "------------------------------------------"
echo "Compilando y enlazando..."
echo "------------------------------------------"

gcc -o codigoC codigoC.c;

echo "------------------------------------------"
echo "Ejecutando Codigo Generado"
echo "------------------------------------------"

./codigoC