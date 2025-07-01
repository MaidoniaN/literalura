# LiterAlura

## Descripción

LiterAlura es una aplicación de consola desarrollada en Java con Spring Boot que permite buscar libros por título consumiendo la API pública de [Gutendex](https://gutendex.com/books/). Los datos de los libros y autores son almacenados en una base de datos PostgreSQL local. La aplicación permite además consultar libros y autores almacenados, así como realizar filtros específicos como autores vivos en cierto año o listar libros por idioma.

---

## Características principales

- Búsqueda de libros por título consumiendo la API pública Gutendex.
- Almacenamiento en base de datos PostgreSQL de libros y autores, con relación Many-to-One entre autor y libros.
- Consultas básicas para listar libros, autores, autores vivos en un año, y libros por idioma.
- Manejo de entrada por consola con menú interactivo.
- Uso de variables de entorno para proteger credenciales de acceso a base de datos.

---

## Arquitectura del proyecto

El proyecto está organizado bajo el paquete principal `com.aluradesafios.literalura` con la siguiente estructura de paquetes:

- **modelo**: Clases que representan las entidades del dominio y los records que mapean la respuesta de la API.
  - `Autor` (Entidad JPA)
  - `Libro` (Entidad JPA)
  - `CategoriaIdioma` (ENUM para los idiomas)
  - `DatosAutor`, `DatosLibro`, `DatosResultado` (records para deserializar JSON)

- **principal**: Clase principal que contiene el menú interactivo y la lógica para la interacción con el usuario y la base de datos.
  - `Principal`

- **repository**: Interfaces que extienden `JpaRepository` para la persistencia de entidades.
  - `AutorRepository`
  - `LibroRepository`

- **service**: Clases para consumir la API externa y convertir datos JSON a objetos Java.
  - `ConsumoAPI` (Cliente HTTP)
  - `ConvierteDatos` (Servicio de deserialización JSON)
  - `IConvierteDatos` (Interfaz para el servicio de conversión)

---

## Tecnologías y herramientas utilizadas

- Java 17 (o superior)
- Spring Boot (creado con [Spring Initializr](https://start.spring.io/))
- Maven para gestión de dependencias y compilación
- PostgreSQL como motor de base de datos
- Hibernate / JPA para persistencia
- API externa: [Gutendex](https://gutendex.com/books/)
- Jackson para manejo de JSON
- Uso de variables de entorno para credenciales sensibles
- Consola como interfaz de usuario

---

## Configuración del proyecto

Se utiliza un archivo `application.properties` ubicado en `src/main/resources` con la siguiente configuración base para conexión a PostgreSQL:

```properties
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_LITERALURA}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
Nota: Las variables ${DB_HOST}, ${DB_LITERALURA}, ${DB_USER}, ${DB_PASSWORD} deben estar definidas como variables de entorno en tu sistema para proteger las credenciales.
