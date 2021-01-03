#!/bin/bash

echo "Compilando y enlazando..."

nasm -f elf $1.asm;
ld -m elf_i386 -s -o $1 $1.o

echo "Ejecutando..."
echo "-----------------------------------------"

./$1