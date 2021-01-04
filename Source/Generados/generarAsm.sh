#!/bin/bash

echo "Compilando y enlazando..."

nasm -f elf codigoAsm.asm;
ld -m elf_i386 -s -o codigoAsm codigoAsm.o

echo "Ejecutando..."
echo "-----------------------------------------"

./codigoAsm