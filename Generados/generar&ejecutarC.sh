#!/bin/bash

echo "Compilando y enlazando..."

gcc -o CodigoC/$1 Ejecutables/$1.c;

echo "Ejecutando..."
echo "-----------------------------------------"

./Generados/$1