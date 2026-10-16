# Sistema de Gestión Bibliotecaria - TP3

Proyecto desarrollado para el Seminario de Práctica de Licenciatura en Informática.

## Descripción

Sistema de gestión y monitoreo bibliotecario para la Biblioteca "Dr. Antonio de la Cruz". Permite administrar socios, libros, préstamos, devoluciones y solicitudes de compra, con persistencia en MySQL.

## Tecnologías

- Java (POO)
- Swing (interfaz gráfica)
- MySQL (base de datos)
- JDBC (conexión)

## Cómo ejecutar

1. Importar el proyecto en IDE preferido.
2. Verificar que el driver JDBC esté en la carpeta `lib/`.
3. Crear la base de datos `biblioteca` en MySQL y ejecutar el script SQL.
4. Ejecutar `GestionBiblioteca.java`.

## Base de datos

El archivo `sql/biblioteca.sql` contiene la estructura completa de la base de datos
con datos de ejemplo. Para importarlo:

1. Abrir phpMyAdmin.
2. Crear la base de datos `biblioteca`.
3. Clic en la pestaña "Importar".
4. Seleccionar el archivo `sql/biblioteca.sql`.
5. Clic en "Continuar".
6. Ejecutar el programa `GestionBiblioteca.java`.

## Autor

Agustín Haredes
