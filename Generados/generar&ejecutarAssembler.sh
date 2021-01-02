#!/bin/bash

echo "Compilando y enlazando..."

nasm -f elf Assembler/$1.asm;
ld -m elf_i386 -s -o Ejecutables/$1 Assembler/$1.o

echo "Ejecutando..."
echo "-----------------------------------------"

./Ejecutables/$1