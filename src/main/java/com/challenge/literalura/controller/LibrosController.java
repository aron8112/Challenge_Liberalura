package com.challenge.literalura.controller;

import com.challenge.literalura.dto.AutorDTO;
import com.challenge.literalura.dto.LibroDTO;
import com.challenge.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LibrosController {

  private final LibroService service;

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

  public void contarLibrosPorIdioma() {
    Map<String, Long> librosPorIdioma = service.contarLibrosPorIdioma();
    librosPorIdioma.forEach((idioma, cantidad) ->
        System.out.println("Idioma: " + idioma + ", Cantidad: " + cantidad));
  }
}
