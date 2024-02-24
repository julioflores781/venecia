# venecia
## Descripción

Esta es una aplicación desarrollada en Java 21 con Spring Boot que proporciona una API para gestionar naves espaciales. La documentación de la API se encuentra disponible en [Swagger UI](http://localhost:8080/api/swagger-ui/index.html).

## Configuración

La aplicación utiliza una base de datos H2 en memoria con las siguientes credenciales:

- **URL:** jdbc:h2:mem:testdb
- **Usuario:** venecia
- **Contraseña:** venecia1234

El perfil de `github-actions` está incluido para ejecutar pruebas en GitHub Actions.

## Requisitos

Para poder utilizar esta aplicación, se necesitan los siguientes requisitos:

- Java 21
- Docker
- Maven

## Instrucciones de Uso

1. Clona el repositorio.
2. Navega al directorio del proyecto.
3. Ejecuta el siguiente comando para construir el proyecto y crear el contenedor Docker:
    ```bash
    mvn clean package
    docker-compose up -d
    ```
4. Una vez que los contenedores estén en funcionamiento, la api estará disponible en [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html).
5. Si desea ejecutar los tests, utiliza el siguiente comando:
    ```bash
    mvn test
    ```
    


## Endpoints

La API proporciona los siguientes endpoints para gestionar naves espaciales:

- **GET /api/spaceships**: Obtiene todas las naves espaciales.
- **GET /api/spaceships/{id}**: Obtiene una nave espacial por su ID.
- **GET /api/spaceships?keyword={keyword}**: Busca naves espaciales por nombre.
- **GET /api/spaceships/pageable?page=0&size=2'**: Obtiene naves espaciales con paginación.
- **POST /api/spaceships**: Crea una nueva nave espacial.
- **PUT /api/spaceships/{id}**: Actualiza una nave espacial existente.
- **DELETE /api/spaceships/{id}**: Elimina una nave espacial por su ID.

Cada endpoint tiene su descripción y los posibles resultados.

## Contacto

Si tienes alguna pregunta o problema, no dudes en contactarme [correo electrónico](mailto:julioflores781@gmail.com).
