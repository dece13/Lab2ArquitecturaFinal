# Lab 2 - Arquitectura de Software

## Proyecto PersonApp - Sistema CRUD

Este proyecto implementa un sistema CRUD completo usando arquitectura hexagonal con Spring Boot, MariaDB y MongoDB.

## Requisitos

- Docker Desktop instalado
- Puerto 8080, 3306 y 27017 disponibles

## Como ejecutar el proyecto

### 1. Clonar el repositorio

git clone https://github.com/dece13/Lab2Arquitectura.git
cd Lab2Arquitectura

### 2. Levantar los contenedores

docker-compose up --build

Espera a que aparezca:
Started PersonAppRestApi in X seconds
Tomcat started on port(s): 8080 (http)

### 3. Probar los endpoints

La API estara disponible en: http://localhost:8080

## Endpoints disponibles

### Persona

- GET http://localhost:8080/api/v1/persona/MARIA - Listar personas
- GET http://localhost:8080/api/v1/persona/MARIA/123456789 - Buscar por ID
- POST http://localhost:8080/api/v1/persona - Crear persona
- PUT http://localhost:8080/api/v1/persona/MARIA/123456789 - Actualizar
- DELETE http://localhost:8080/api/v1/persona/MARIA/123456789 - Eliminar

### Profesion

- GET http://localhost:8080/api/v1/profesion/MARIA - Listar
- POST http://localhost:8080/api/v1/profesion - Crear

### Telefono

- GET http://localhost:8080/api/v1/telefono/MARIA - Listar
- POST http://localhost:8080/api/v1/telefono - Crear

### Estudios

- GET http://localhost:8080/api/v1/estudios/MARIA - Listar
- POST http://localhost:8080/api/v1/estudios - Crear

## Ejemplos de JSON

### Crear Persona

{
  "dni": "123456789",
  "firstName": "Juan",
  "lastName": "Perez",
  "age": "30",
  "sex": "M",
  "database": "MARIA"
}

### Crear Profesion

{
  "id": "1",
  "nombre": "Ingeniero de Software",
  "descripcion": "Desarrollo de software",
  "database": "MARIA"
}

### Crear Telefono

{
  "num": "3001234567",
  "oper": "Claro",
  "duenioId": "123456789",
  "database": "MARIA"
}

### Crear Estudio

{
  "idProf": "1",
  "ccPer": "123456789",
  "fecha": "2020-01-15",
  "univer": "Pontificia Universidad Javeriana",
  "database": "MARIA"
}

## Base de datos

El proyecto soporta dos bases de datos:
- MARIA - MariaDB (puerto 3306)
- MONGO - MongoDB (puerto 27017)

Se pueden usar intercambiando el valor del campo database en los JSON.

## Tecnologias usadas

- Java 11
- Spring Boot 2.7.11
- Maven
- Docker
- MariaDB 10.6
- MongoDB 5.0
- Lombok

## Arquitectura

El proyecto usa arquitectura hexagonal (puertos y adaptadores) con la siguiente estructura:

- domain: Modelos de dominio
- application: Casos de uso
- adapters: Adaptadores de entrada y salida
  - REST API
  - MariaDB
  - MongoDB

## Detener el proyecto

docker-compose down

Para limpiar volumenes:

docker-compose down -v

## Notas

- Los campos de los JSON deben ser strings
- El formato de fecha es YYYY-MM-DD
- Para crear telefonos o estudios, primero deben existir las personas y profesiones relacionadas

---

Desarrollado por Daniel Ceballos - 2025
