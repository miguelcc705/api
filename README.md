API Spring Boot - Calculadora con Autenticación JWT
Este proyecto es una API REST construida con Spring Boot que implementa autenticación mediante JWT (JSON Web Token). Incluye funcionalidades de registro e inicio de sesión de usuarios, así como una calculadora protegida por token.

Tecnologías utilizadas
Java 17
Spring Boot 3.4.5
Spring Security
JPA (Hibernate)
MySQL
JWT (jjwt)

Características principales
✅ Registro y login de usuarios

✅ Generación y validación de tokens JWT

✅ Rutas protegidas con autorización tipo Bearer Token

✅ Endpoints para operaciones matemáticas (calculadora)

Estructura del proyecto
controller: Controladores REST

service: Lógica de negocio

model: Entidades JPA

repository: Interfaces para acceso a datos

security: Filtros, configuraciones y manejo de JWT

Endpoints disponibles
📚 Autenticación
POST /api/auth/register: Registrar nuevo usuario

POST /api/auth/login: Iniciar sesión y obtener token JWT

📚  Calculadora (rutas protegidas)
POST /api/calculate: Realiza una operación (ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, POWER, SQUARE_ROOT)

📚 Endpoints de Historial (rutas protegidas)
GET /api/history: Consulta el historial (paginado, filtrado)

GET /api/history/{id}: Consulta una operación específica

DELETE /api/history/{id}: Elimina una operación

## 📦 Clonación e instalación del proyecto

```bash
git clone https://github.com/tu_usuario/api.git
cd calculadora-api

⚙️ Configuración del entorno
Base de datos MySQL
Crea una base de datos llamada calculadora:

CREATE DATABASE calculadora;

⚙️ Configura el archivo application.properties:

Ubicación: src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/calculadora
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

▶️ Ejecución del proyecto
Desde el archivo Apiapplication.java haz clic en Run

📂 Colección Postman
Se incluye una colección Postman en el repositorio:
postman/CalculatorApi.postman_collection.json

Ejemplos con httpie

http POST http://localhost:8080/api/auth/register \
  username=test2 email=test@example.com password=654321

http POST http://localhost:8080/api/auth/login \
  username=test2 password=654321

http POST http://localhost:8080/api/calculate \
  Authorization:"Bearer <TOKEN>" \
  operation==MULTIPLICATION operandA:=7.5 operandB:=4.2

http GET "http://localhost:8080/api/history?page==0&size==5&sort==timestamp,asc" \
  Authorization:"Bearer <TOKEN>"

http DELETE http://localhost:8080/api/history/{id} \
  Authorization:"Bearer <TOKEN>"

## 📦 Anotaciones adicionales

- Los metodos de register y login no requieren la validacion de Jwt
- El metodo de login retorna el token que debe usarse para el resto de peticiones
- Este token debe ponerse como un bearer token al momento de realizar calculos o consultas del historial

