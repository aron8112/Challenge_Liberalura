package com.challenge.literalura.principal;

import com.challenge.literalura.controller.LibrosController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuMethods {
  private Scanner userInput = new Scanner(System.in);
  private final LibrosController controller;
  String opcionesDeBusqueda;

  @Autowired
  public MenuMethods(LibrosController controller) {
    this.controller = controller;
  }

  public void buscarLibrosPorTitulo(){
    System.out.println("-> A continuación escriba el título del libro: ");
    var tituloBuscado = userInput.nextLine();
    opcionesDeBusqueda = tituloBuscado.replace(" ","%20");
    System.out.println("-> Si lo conoce, escriba el nombre del autor (sino oprima Enter): ");
    var autorBuscado = userInput.nextLine();
    if(!autorBuscado.isEmpty()){
      opcionesDeBusqueda = opcionesDeBusqueda +"%20" + autorBuscado.replace(" ","%20");
    }
    controller.buscarLibro(opcionesDeBusqueda);
  }

  public void listarLibrosBuscados(){
    controller.listarLibros();
  }

  public void listarAutoresBuscados(){
    controller.listarAutores();
  }

  public void buscarAutorVivoPorAnio(){
    System.out.println("¿Desea buscar los resultados en la base de datos? Y/N:");
    var respuesta = userInput.nextLine();
    switch (respuesta.toLowerCase()){
      case "y":
        System.out.println("==== Búsqueda entre los autores guardados ====");
        System.out.println("-> A continuación escriba el año de búsqueda: ");
        var anioBuscado = userInput.nextInt();
        controller.buscarAutoresPorAnio(anioBuscado);
        break;
      case "n":
        System.out.println("Para buscar en la web, ingrese un año de inicio:");
        var anioInicio = userInput.nextLine();
        System.out.println("A continuación, el año para el fin de búsqueda:");
        var anioFinal = userInput.nextLine();
        controller.busquedaWebEntreAnios(anioInicio, anioFinal);
        break;
      default:
        System.out.println("Solo Y o N");
    }
  }

  public void buscarCantidadDeLibrosPorIdioma(){
    System.out.println("¿Desea buscar los resultados en la base de datos? Y/N:");
    var respuesta = userInput.nextLine().toLowerCase();
    switch (respuesta.toLowerCase()){
      case "y":
        controller.contarLibrosPorIdioma();
        break;
      case "n":
        System.out.println("Para buscar en la web, ingrese un idioma:");
        System.out.println("(Para obtener mejores resultados, ingrese la abreviación de dos letras)");
        var idioma = userInput.nextLine();
        controller.busquedaWebIdioma(idioma);
        break;
      default:
        System.out.println("Solo Y o N");
    }
  }

  //EXTRAS
  public void generarEstadisticas(){
    controller.mostrarEstadisticas();
  }
  public void top10LibrosDescargados(){
    System.out.println("¿Desea buscar los resultados en la base de datos? Y/N:");
    var respuesta = userInput.nextLine().toLowerCase();
    switch (respuesta.toLowerCase()){
      case "y":
        controller.top10LibrosBD();
        break;
      case "n":
        controller.top10LibrosApi();
        break;
      default:
        System.out.println("Solo Y o N");
    }
  }
  public void buscarAutorPorNombre(){
    System.out.println("-> A continuación escriba el nombre del autor buscado: ");
    var autorBuscado = userInput.nextLine();
    controller.listarAutoresBuscadosOrdenados(autorBuscado);
  }
  public void listarAutoresConOpciones(){
    System.out.println("====== Listar autores con sus títulos guardados ======");
    String nombre = userInput.nextLine();
    controller.mostrarAutoresConTitulos(nombre);
  }
}
