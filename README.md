# Hospital Management System - Kosmos Challenge

Este proyecto es una aplicación web para la gestión de citas médicas en un hospital, desarrollada como parte del ejercicio técnico para Kosmos.

## 🚀 Tecnologías utilizadas

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* H2 Database
* Thymeleaf
* Bootstrap (CDN)
* Maven

## 📦 Estructura del proyecto

* `model/` → Entidades JPA (`Doctor`, `Consultorio`, `Cita`)
* `repository/` → Interfaces JPA (`DoctorRepository`, `ConsultorioRepository`, `CitaRepository`)
* `service/` → Servicios con lógica de negocio y validaciones
* `controller/` → Controladores REST y vistas Thymeleaf
* `templates/` → Vistas Thymeleaf con diseño responsivo
* `application.properties` → Configuración de la base de datos H2

## 💻 Cómo ejecutar el proyecto

1. Clona el repositorio:

   ```
   git clone https://github.com/Jorge-Levi/prueba-tecnica-kosmos.git
   ```

2. Ingresa al directorio:

   ```
   cd hospitalmanagement
   ```

3. Compila y ejecuta:

   ```
   mvn clean install
   mvn spring-boot:run
   ```

4. Abre en tu navegador:

   * Web: [http://localhost:8080](http://localhost:8080)
   * H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

      * JDBC URL: `jdbc:h2:mem:testdb`
      * Usuario: `sa`
      * Contraseña: `password`

## 📲 Endpoints principales

### Web (Thymeleaf)

* [http://localhost:8080/citas](http://localhost:8080/citas) → Lista de citas
* [http://localhost:8080/cita/form](http://localhost:8080/cita/form) → Formulario de nueva cita
* [http://localhost:8080/doctores](http://localhost:8080/doctores) → Lista de doctores
* [http://localhost:8080/doctor/form](http://localhost:8080/doctor/form) → Formulario de nuevo doctor
* [http://localhost:8080/consultorios](http://localhost:8080/consultorios) → Lista de consultorios
* [http://localhost:8080/consultorio/form](http://localhost:8080/consultorio/form) → Formulario de nuevo consultorio

### API REST

* GET/POST [http://localhost:8080/api/citas](http://localhost:8080/api/citas)
* GET/POST [http://localhost:8080/api/doctores](http://localhost:8080/api/doctores)
* GET/POST [http://localhost:8080/api/consultorios](http://localhost:8080/api/consultorios)

## ✅ Funcionalidades implementadas

* Alta, edición y eliminación de doctores, consultorios y citas
* Validaciones en backend (reglas de negocio y anotaciones JPA)
* Interfaz web responsiva con Bootstrap
* Manejo de errores con mensajes amigables
* Base de datos en memoria (H2) para pruebas rápidas

## 🧪 Pruebas recomendadas

* Postman para probar los endpoints REST
* Navegador para probar los formularios y las listas
* H2 Console para validar datos en la base

## 🙌 Autor

Realizado por Jorge Levi Tapia Lugardo como ejercicio técnico para Kosmos.
