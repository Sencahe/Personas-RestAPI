# Personas-RestAPI

RestAPI para el manejo del recurso Persona

# Requerimientos
- JDK 16
- Maven
- Docker
- Docker-compose

# Instalación    

1- Descargar repositorio

2- Compilar aplicación. Este paso se puede saltear ya que en el repositorio está incluida la última versión compilada en la carpeta /target. Se requiere tener JDK16 y Maven instalado.

Opcionalmente se puede cambiar la versión del JDK desde el archivo pom.xml. En tal caso tambien habria que modificarlo en el archivo “Dockerfile”, en la cláusula FROM que indica la imagen origen con la versión del JDK

Se puede compilar abriendo el proyecto con NetBeans y ejecutando la acción “Clean and Build”. Si se compila de esta manera, no haría falta tener maven instalado. 

Sino, abrir la consola ubicándose en la carpeta raíz del proyecto y ejecutar el comando: “mvn clean package”

Este comando va a descargar las dependencias de maven y compilar la aplicación en un .jar que veremos en target/personas-rest-api-1.0.jar”

3- Dockerizar aplicación y base de datos. Se requiere tener instalado tanto Docker como Docker-Compose 

Asegurarse de tener libre los puertos 8080 y 5432

Abrir la consola ubicándose en la carpeta raíz del proyecto y ejecutar el comando: “docker compose up -d”

Este comando va a tomar el archivo “docker-compose.yaml” que se encuentra en la raíz del proyecto. El mismo incluye una imagen pública de postgres y una imagen de la aplicación que se generará partir del archivo “Dockerfile” y del jar “target/personas-rest-api-1.0.jar”.

Revisar que se hayan creado los contenedores con el comando “docker ps” o viéndolo desde Docker Desktop si utilizan windows

4- Ingresar a “http://localhost:8080/swagger-ui/index.html” en el navegador para probar la conexión local y los endpoints disponibles
