# Challenge FOROHUB

Esta API simula el funcionamiento de un foro a través de operaciones CRUD.

## Requisitos
Para ejecutar el programa en local es necesario:
- Contar con Java (para la ejecución del archivo comprimido **JAR**)
- Contar con MySQL y crear una Base de Datos
- Configurar las siguientes variables de entorno:
  - ```DB_HOST``` para el nombre del servidor local (generalmente ```localhost```)
  - ```DB_NAME``` para el nombre de la Base de Datos (aconsejamos usar nombre ```forohub``` para la Base de Datos)
  - ```DB_USER``` para el nombre de usuario del usuario que administra la Base de Datos (por defecto, ```root```)
  - ```DB_PASSWORD``` para la contraseña del usuario que administra la Base de Datos

## Opciones y uso
El programa permite al usuario las siguientes operaciones:
- Añadir, editar, listar, deshabilitar y ver a detalle los **tópicos** en la Base de Datos.
- Añadir, editar, listar, eliminar y ver a detalle las **respuestas** a los **tópicos** la Base de Datos.
- Crear **usuarios**.
- Autenticarse (requerido para emplear la mayoría de los *endpoints*)

Para un uso más detallado de los *endpoints* disponibles, puede revisarse la documentación en **Swagger** en la ruta ```/swagger-ui.html``` del servidor local una vez ejecutado el programa.

Si desea únicamente **ejecutar el programa** y no explorar el código del proyecto, puede ejecutar el archivo **JAR** en el directorio ```/target``` del proyecto con el siguiente comando:

~~~
java -jar .\target\forohub-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
~~~

