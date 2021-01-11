# Compilador
Proyecto final de compiladores 2, aceptando lenguaje python, visualBasic, java y c, generando codigo 3 direcciones y codigo ensamblador. (ejecutables)

## Comenzando 🚀

Clonaremos el repositorio o descargar el proyecto.zip (descomprimiéndolo en la carpeta que desee)

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_JDK 13 (https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)_
_Maven (hará falta construir el proyecto para solucionar las posibles dependencias)_
_Netbenas IDE 12.1 (Utilizada para el desarrollo y para resolver posibles conflictos)_
_gcc (Compilador de codigo C y de GNU Assembler)_

### Instalación 🔧

Es necesaria la instalación de los paquetes mencionados anteriormente. 
Vamos a verificar si no tenemos instalada una version de Java en el equipo, así como el compilador de assembler y el de nasm (netwide assembler).
Ejecutando los siguientes comandos en consola

```
java -version (JDK 13)
gcc -v (10.2.0)
```
Cada uno de estos comandos nos retornará la version que tenemos instalada, si alguno genera error, será necesario hacer una instalación previa de los recursos antes.

Para la construccion del proyecto, es decir, resolver dependencias propias para la ejecución del proyecto vamos a proceder a usar maven

Dados posibles conflictos durante el desarrollo, al ejecutar el codigo 3 direcciones y el codigo assembler generado se abrirá una carpeta en la que se encuentran dos archivos bash, estos compilan y ejecutan los codigos generados, abriendo la consola ejecutamos:

Para compilar y ejecutar el codigo Assembler generado
```
./generarAsm.sh
```
Para compilar y ejecutar el codigo 3 direcciones generado
```
./generarC.sh
```

Cabe resaltar que es necesario que previamente se haya seleccionado la opcion de Generar del codigo que se desea ejecutar correspondientemente, ejecutar estos archivos puede requerir permisos especiales por lo tanto ejecutaremos los siguientes comandos para otorgar los permisos de compilación y ejecución

```
sudo chmod +x ejecutarC.sh
sudo chmod +x ejecutarAsm.sh
```

## Ejecutando las pruebas ⚙️

_El proyecto trae consigo varios archivos de entrada para hacer pruebas, estos se encuentran dentro de la carpeta "Compilador/entradas", estos son archivos libres de errores para poder generar el código 3 direcciones._

## Despliegue 📦

_Si se tiene instalado netbeans, podemos ejecutar el código desde esta ubicación siempre

## Autores ✒️

* **José Carlos Soberanis Ramírez** - *Estudiante Ing. en ciencias y sistemas*
