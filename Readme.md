# Challenge Literalura

### Desafío del Curso Java Alura Latam-Oracle Next Education

## Descripción del proyecto

Desarrollar un Catálogo de Libros que ofrezca interacción textual (vía consola) con los usuarios, proporcionando diversas opciones de interacción. Los libros se buscarán a través de la API Gutendex. 
El menú de interacción es el siguiente: 
````
        ===========================================
        =============== LITERALURA ================
        ===========================================

          A continuación elija la opción que desee: 
          
          1.- Búsqueda de libro por título.
          2.- Listado de libros consultados.
          3.- Listado de autores.
          4.- Buscar autores vivos en un año particular.
          5.- Consultar cantidad de libros por idioma. 
          
          Extras:
          6.- Generar estadísticas.
          7.- Top 10 libros más descargados. 
          8.- Buscar autor por nombre. 
          9.- Listado de autores con opciones. 
          
          0.- Salir.
````
1. El punto 1 buscará en la API de acuerdo a uno o dos criterios de búsqueda en el endpoint: 
```
?search=<criterio1>&<criterio2>
```
Ejecutará la llamada, guardará el registro de la primera página de búsqueda y mostrará en consola el resultado (si lo hubiera), sino un mensaje: "Sin resultados, por favor pruebe con otra opción."

2. Mostrará el listado de los libros guardados en la base de datos.
3. Mostrará el listado de los autores guardados en la base de datos.

Las siguientes opciones incluyen la posibilidad de elegir entre consultar en la base de datos o la API:
4. Buscará autores en la base de datos, o en la API: 
```
?author_year_start="+anioInicio+"&author_year_end="+anioFinal
```
5. Buscará los libros por idiomas en la base de datos, o en la API:
```
"?languages=" + <idioma> (+&page=<numPage>) --> si se consultan distintas opciones
```    
En este caso podrá navegar entre los resultados (anterior o siguiente) o elegir guardar una página de resultados

6. Generará las estadisticas de los libros presentes en la base de datos. 

7. Top ten de libros entre los presentes en la base de datos o las descargas más populares de la API
```
"?sort=popular
```  
8. Buscará si hay un autor presente en la base de datos.
9. Devolverá si el autor está en la base de datos y los títulos asociados a este. 

## Tecnologías utilizadas

* Docker para la base de datos. En el archivo docker-compose.yml
* PostgreSQL como base de datos.
* FasterXML-Jackson para convertir la respuesta JSON a objetos JAVA.
* Spring Data JPA (Java Persistence API) como ORM. 

## Autor
Aron Siccardi