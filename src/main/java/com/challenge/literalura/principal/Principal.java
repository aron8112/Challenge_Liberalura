package com.challenge.literalura.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
  private Scanner userInput = new Scanner(System.in);
  private final MenuMethods menuMethods;

  @Autowired
  public Principal(MenuMethods menuMethods) {
    this.menuMethods = menuMethods;
  }

  public void muestraElMenu() {
    var opcion = -1;
    while (opcion != 0) {
      var menu = """
          \n
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
          """;
      System.out.println(menu);
      opcion = userInput.nextInt();
      userInput.nextLine();

      switch (opcion){
        case 1:
          menuMethods.buscarLibrosPorTitulo();
          break;
        case 2:
          menuMethods.listarLibrosBuscados();
          break;
        case 3:
          menuMethods.listarAutoresBuscados();
          break;
        case 4:
          menuMethods.buscarAutorVivoPorAnio();
          break;
        case 5:
          menuMethods.buscarCantidadDeLibrosPorIdioma();
          break;
        case 6:
          menuMethods.generarEstadisticas();
          break;
        case 7:
          menuMethods.top10LibrosDescargados();
          break;
        case 8:
          menuMethods.buscarAutorPorNombre();
          break;
        case 9:
          menuMethods.listarAutoresConOpciones();
          break;
        case 0:
          System.out.println("Cerrando la aplicación...");
          break;
        default:
          System.out.println("Opción inválida");
      }
    }
  }
}
