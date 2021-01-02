#!/bin/bash

echo "Compilando y enlazando..."

gcc -o $1 $1.c;

echo "Ejecutando..."
echo "-----------------------------------------"

./$1