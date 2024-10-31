package com.challenge.literalura.controller;

import com.challenge.literalura.dto.AutorDTO;
import com.challenge.literalura.dto.LibroDTO;
import com.challenge.literalura.model.DatosResAPI;
import com.challenge.literalura.model.Idioma;
import com.challenge.literalura.model.Libro;
import com.challenge.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class LibrosController {

  private final LibroService service;
  private int numPage = 0;
  private DatosResAPI response;
  private final String langBusqueda = "?languages=";

  @Autowired
  public LibrosController(LibroService service) {
    this.service = service;
  }

  public void buscarLibro(String opcionesDeBusqueda){
    String urlBusqueda = "?search="+opcionesDeBusqueda;

    try{
      service.buscarLibro(urlBusqueda);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void listarLibros() {
    try{
      List<LibroDTO> titulos = service.mostrarLibros();
      titulos.forEach(t -> System.out.println("_______________________\nTítulo: "+t.title()+" || Autor: "+t.nombreAutor()+" || Gutendex ID: "+t.apiId()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void listarAutores(){
    try{
      List<AutorDTO> autores = service.mostrarAutores();
      autores.forEach(a -> System.out.println("_______________________\nAutor: "+a.nombre()+" || Año Nacimiento: "+a.anioNacimiento()+" || Año Fallecimiento: "+a.anioMuerte()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void buscarAutoresPorAnio(int anioBuscado) {
    try{
      List<AutorDTO> autores = service.buscarAutoresPorAnio(anioBuscado);
      autores.forEach(a -> System.out.println("_______________________\nAutor: "+a.nombre()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // BUSQUEDA DE LIBROS POR IDIOMA
  // EN LA BASE DE DATOS
  public void contarLibrosPorIdioma() {
    Map<String, Long> librosPorIdioma = service.contarLibrosPorIdioma();
    librosPorIdioma.forEach((idioma, cantidad) ->
        System.out.println("Idioma: " + Idioma.getIdiomaOmdb(idioma) + ", Cantidad: " + cantidad));
  }
  // EN LA API DE GUTENDEX
  public void busquedaWebIdioma(String idioma) {

    var apiResponse = service.busquedaWebPorIdioma(langBusqueda+idioma);
    var input = new Scanner(System.in);
    String userResponse = "";

    //Captura la primera llamada, si hay error vuelve al menú
    if(apiResponse.contador() != 0){
      ejecutarResApi(idioma);
    } else {
      System.out.println("No se encontraron resultados para el idioma: "+idioma);
      return;
    }

    while (!userResponse.equals("exit")) {
      userResponse = input.nextLine().toLowerCase();
      procesarRespuesta(userResponse, idioma);
      complementarRespuesta();
    }
  }

  private void ejecutarResApi (String idioma){
    setResponse(service.busquedaWebPorIdioma(langBusqueda+idioma ));
    mostrarResultados();
    complementarRespuesta();
  }

  private void mostrarResultados() {
    System.out.println("------------------------------------------");
    System.out.println("Libros encontrados: " + response.contador());
    System.out.println("------------------------------------------");
    response.results().forEach(r -> System.out.println(
        "\n Título: " + r.title() + ", Autor: " +
            ( r.authors().isEmpty() ? "Desconocido" : r.authors().get(0).nombre())));
  }

  private void procesarRespuesta(String userResponse, String idioma) {

    switch (userResponse) {
      case "s":
        service.guardarLibros(response.results());
        System.out.println("Guardando...");
        break;
      case "n":
        if (response.nextResult() != null) {
          setNumPage(numPage+1);
          setResponse(service.busquedaWebPorIdioma("?languages=" + idioma+"&page="+getNumPage()));
          mostrarResultados();
        } else {
          System.out.println("No hay más resultados siguientes.");
        }
        break;
      case "p":
        if (response.previousResult() != null) {
          setNumPage(numPage-1);
          setResponse(service.busquedaWebPorIdioma("?languages=" + idioma+"&page="+getNumPage()));
          mostrarResultados();
        } else {
          System.out.println("No hay resultados previos.");
        }
        break;
      default:
        System.out.println("Opción inválida");
        break;
    }
  }

  public void complementarRespuesta(){
    System.out.println("\n------\n¿Desea guardar los resultados o seguir viendo resultados?");
    System.out.println("Si desea salir escriba 'exit'");
    if(response.previousResult() == null) {
      System.out.println("Oprima 's' para guardar o 'n' para ver el siguiente resultado");
    } else if (response.nextResult() == null) {
      System.out.println("Oprima 's' para guardar o 'p' para volver atrás");
    } else {
      System.out.println("Oprima 's' para guardar, 'n' para ver el siguiente resultado, o 'p' para volver atrás");
    }
  }

  // BUSQUEDA DE AUTORES POR AÑO
  // EN LA BASE DE DATOS
  public void listarAutoresBuscadosOrdenados(String nombre){
    List<AutorDTO> autores = service.buscarAutoresConsultadosPorAnio(nombre);
    if (!autores.isEmpty()){
      autores.forEach(autor ->
          System.out.println("Nombre: " + autor.nombre() + ", Año de Nacimiento: " + autor.anioNacimiento()));
    } else {
      System.out.println("No hay autores consultados con ese nombre.");
    }
  }
  // EN LA API DE GUTENDEX
  public void busquedaWebEntreAnios(String anioInicio, String anioFinal) {
    String urlAniosBusqueda = "?author_year_start="+anioInicio+"&author_year_end="+anioFinal;
    System.out.println(urlAniosBusqueda);
  }

  // MOSTRAR TÍTULOS RELACIONADOS A UN AUTOR
  public void mostrarAutoresConTitulos(String nombre) {
    var autores = service.mostrarAutoresConTitle(nombre);
    if(autores == null){
      System.out.println("No se encuentra ese autor.");
    } else {
      autores.forEach((autorDTO, strings) -> System.out.println(
          "Autor: "+autorDTO.nombre()+"\nTítulos: "+ strings));
    }

  }

  public int getNumPage() {
    return numPage;
  }

  public void setNumPage(int numPage) {
    this.numPage = numPage;
  }

  public DatosResAPI getResponse() {
    return response;
  }

  public void setResponse(DatosResAPI response) {
    this.response = response;
  }

  public void top10LibrosBD() {
    List<Libro> top10Libros = service.top10LibrosBd();

    top10Libros.forEach(System.out::println);
  }

  public void top10LibrosApi() {
    String sortSearch = "?sort=popular";
    var response = service.busquedaTop10Api(sortSearch);
    response.forEach(libro -> {
      System.out.println("\nTítulo: " + libro.get("title") + ",");
      System.out.println("Cantidad de descargas: " + libro.get("descargasNum") + ",");
      System.out.println("Autor: " + libro.get("author") + ",");
      System.out.println("Gutendex Id: " + libro.get("apiId"));
    });;
  }

  public void mostrarEstadisticas (){
    DoubleSummaryStatistics estadisticas = service.mostrarEstadisticasLibros();

    System.out.println("\n----------------------------------");
    System.out.println("    Estadísticas de descargas");
    System.out.println("----------------------------------");
    System.out.println("Cantidad de libros guardados: " + estadisticas.getCount());
    System.out.println("Total de descargas: " + estadisticas.getSum());
    System.out.println("Promedio de descargas: " + estadisticas.getAverage());
    System.out.println("Descargas mínimas: " + estadisticas.getMin());
    System.out.println("Descargas máximas: " + estadisticas.getMax());
  }
}
