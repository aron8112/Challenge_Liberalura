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
    System.out.println("-> A continuación escriba el año de búsqueda: ");
    var anioBuscado = userInput.nextInt();
    controller.buscarAutoresPorAnio(anioBuscado);
  }
  public void buscarCantidadDeLibrosPorIdioma(){
    controller.contarLibrosPorIdioma();
  }

  //EXTRAS
  public void generarEstadisticas(){
    System.out.println("Generar Estadisticas");
  }
  public void top10LibrosDescargados(){
    System.out.println("Libros más descargados");
  }
  public void buscarAutorPorNombre(){
    System.out.println("Buscar autor por nombre");
  }
  public void listarAutoresConOpciones(){
    System.out.println("Listar autores con condiciones");
  }
}
