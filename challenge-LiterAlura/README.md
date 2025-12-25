# Challenge LiterAlura

El programa se conecta a la API **Gutendex** para obtener datos de libros en Project Gutenberg

## Requisitos
Para ejecutar el programa en local es necesario:
- Contar con PostgreSQL y crear una Base de Datos
- Configurar las variables de entorno empleadas en el archivo ```application.properties```, las cuales son:
  - ```DB_HOST``` para el nombre del servidor local (generalmente ```localhost```)
  - ```DB_NAME``` para el nombre de la Base de Datos (aconsejamos usar nombre ```literalura``` para la Base de Datos)
  - ```DB_USER``` para el nombre de usuario del usuario que administra la Base de Datos (por defecto, ```postgres```)
  - ```DB_PASSWORD``` para la contraseña del usuario que administra la Base de Datos

## Opciones y uso
El programa permite al usuario
- Buscar libros en Gutendex, que se añaden a la Base de Datos local cuando aun no existen en ella
- Mostrar todos los libros en la Base de Datos local
- Mostrar todos los autores en la Base de Datos local
- Mostrar aquellos autores en la Base de Datos local que se encontraban vivos en un año específico
- Mostrar los libros en la Base de Datos local que están en un idioma específico
