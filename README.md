# venecia
## Descripción

Esta es una aplicación desarrollada en Java 21 con Spring Boot que proporciona una API para gestionar naves espaciales. La documentación de la API se encuentra disponible en [Swagger UI](http://localhost:8080/api/swagger-ui/index.html).

## Configuración

La aplicación utiliza una base de datos MS SQL Server con la siguiente configuración:

- **URL:** jdbc:sqlserver://host.docker.internal:1433;databaseName=master;encrypt=true;trustServerCertificate=true;
- **Usuario:** sa
- **Contraseña:** Veecia1234
- **Driver:** com.microsoft.sqlserver.jdbc.SQLServerDriver

Para la ejecución de los tests, se utiliza una base de datos H2 en memoria con las siguientes credenciales:

- **URL:** jdbc:h2:mem:testdb
- **Usuario:** venecia
- **Contraseña:** venecia1234

Es importante destacar que la base de datos MS SQL Server está configurada dentro del archivo Docker Compose. Por lo tanto, al iniciar los contenedores, esta base de datos se inicializará y creará el esquema y los datos necesarios automáticamente.


El perfil de `github-actions` está incluido para ejecutar pruebas en GitHub Actions y tambien para correr los test.

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
    mvn clean package -DskipTests
    docker-compose up -d
    ```
4. Una vez que los contenedores estén en funcionamiento, la API estará disponible en [http://localhost:8080/api/](http://localhost:8080/api/). Es importante tener en cuenta que la API está securizada, por lo que es necesario registrarse o iniciar sesión con un usuario ya registrado. Aquí se detallan los pasos para realizar estas acciones:
   
   Coleccion de postman en `src/main/resources/static/venecia.postman_collection.json`
   
   - **Registro de usuario:**
    ```bash
    curl --location 'http://localhost:8080/api/auth/register' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "username": "usuario@hotmail.com",
      "lastname": "usuario",
      "firstname": "nombre",
      "country": "Madrid",
      "password": "12345"
    }'
    ```
   Este endpoint te retornará un JWT si el registro fue exitoso.

   - **Inicio de sesión:**
    ```bash
    curl --location 'http://localhost:8080/api/auth/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "username": "usuario@hotmail.com",
      "password": "12345"
    }'
    ```
   Este endpoint te retornará un JWT que será necesario para utilizar el resto de los endpoints.

   En la ruta `src/main/resources/static/venecia.postman_collection.json` encontrarás un archivo `venecia.postman_collection.json` con todos los endpoints y algunas configuraciones que te ayudarán a probar cada uno de ellos. No es necesario que copies y pegues el token en cada uno de los endpoints, ya que está configurado con una variable que se establece automáticamente después del registro o inicio de sesión.

    ```javascript
    // Parsear la respuesta JSON para obtener el token
    let jsonData = pm.response.json();
    let token = jsonData.token;

    // Guardar el token en una variable de entorno llamada 'auth_token'
    pm.environment.set('auth_token', token);
    ```
5. Si desea ejecutar los tests, utilice el siguiente comando:
    ```bash
    mvn test -Dspring.profiles.active=github-actions
    ```
   Se ha creado un perfil para ejecutar los tests contra una base de datos en memoria H2.

    


## Endpoints

La API proporciona los siguientes endpoints para gestionar naves espaciales:


- **POST /api/auth/register**: Registrarte y autenticarte.
- **POST /api/auth/login**: Autenticarte.
- **GET /api/spaceships/**: Obtiene todas las naves espaciales.
- **GET /api/spaceships/{id}**: Obtiene una nave espacial por su ID.
- **GET /api/spaceships?keyword={keyword}**: Busca naves espaciales por nombre.
- **GET /api/spaceships/pageable?page=0&size=2'**: Obtiene naves espaciales con paginación.
- **POST /api/spaceships/**: Crea una nueva nave espacial.
- **PUT /api/spaceships/{id}**: Actualiza una nave espacial existente.
- **DELETE /api/spaceships/{id}**: Elimina una nave espacial por su ID.

Cada endpoint tiene su descripción y los posibles resultados.

## Contacto

Si tienes alguna pregunta o problema, no dudes en contactarme [correo electrónico](mailto:julioflores781@gmail.com).
