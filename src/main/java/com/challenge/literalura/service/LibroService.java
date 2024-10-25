package com.challenge.literalura.service;

import com.challenge.literalura.dto.AutorDTO;
import com.challenge.literalura.dto.LibroDTO;
import com.challenge.literalura.model.*;
import com.challenge.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibroService {

  private final GutendexAPI consultaAPI;
  private final DataConversion conversorJson;
  private final LibroRepository repository;
  private final AutorService autorService;

  @Autowired
  public LibroService(
      LibroRepository repository,
      DataConversion conversorJson,
      GutendexAPI consultaAPI,
      AutorService autorService) {
    this.repository = repository;
    this.conversorJson = conversorJson;
    this.consultaAPI = consultaAPI;
    this.autorService = autorService;
  }

  @Transactional
  public void buscarLibro(String params) {
    // 1. Obtener los datos de la API
    List<DatosLibro> listaRespuestaLibros = obtenerDatosLibrosDesdeApi(params);

    // 2. Convertir los datos de la API en entidades Libro
    if(!listaRespuestaLibros.isEmpty()){
    List<Libro> libros = listaRespuestaLibros.stream()
        .map(this::convertirADominioLibro)
        .collect(Collectors.toList());

    // 3. Guardar cada libro en la base de datos
    libros.forEach(repository::save);
    libros.forEach(System.out::println);
    } else {
      System.out.println("Sin resultados, por favor pruebe con otra opción.");
    }
  }

  private List<DatosLibro> obtenerDatosLibrosDesdeApi(String params) {
    var json = consultaAPI.obtenerDatos(params);
    DatosResAPI apiResponse = conversorJson.obtenerDatos(json, DatosResAPI.class);

    return apiResponse.contador() > 0 ? apiResponse.results() : List.of();
  }

  // Método para convertir DatosLibro en una entidad Libro
  private Libro convertirADominioLibro(DatosLibro datosLibro) {
    Libro libro = new Libro(datosLibro);

    // Asociar o crear el autor y asignarlo al libro
    DatosAutor datosAutor = null;
    if (datosLibro.authors() != null && !datosLibro.authors().isEmpty()) {
      datosAutor = datosLibro.authors().get(0);
      Autor autor = autorService.obtenerOCrearAutor(datosAutor);
      libro.setAutor(autor);
    }
    return libro;
  }

  @Transactional
  public List<LibroDTO> mostrarLibros() {

      List<Libro> todosLosLibros = repository.findAll();
      return convierteDatos(todosLosLibros.stream()
          .distinct()
          .collect(Collectors.toMap(Libro::getTitle, libro -> libro, (libro1, libro2) -> libro1))
          .values()
          .stream()
          .collect(Collectors.toList()));
  }

  public List<AutorDTO> mostrarAutores() {
    return autorService.findAll();
  }

  public List<LibroDTO> convierteDatos(List<Libro> serie){
    return serie.stream()
        .map(s-> new LibroDTO(s.getTitle(),s.getApiId(),s.getAutor().getNombre()))
        .collect(Collectors.toList());
  }

  public List<AutorDTO> buscarAutoresPorAnio(int anioBuscado) {
    return autorService.buscarAutoresPorAnio(anioBuscado);
  }

  public Map<String, Long> contarLibrosPorIdioma() {
    List<Object[]> resultados = repository.countBooksByLanguage();

    Map<String, Long> conteoPorIdioma = new HashMap<>();
    for (Object[] resultado : resultados) {
      String idioma = (String) resultado[0];
      Long count = (Long) resultado[1];
      conteoPorIdioma.put(idioma, count);
    }
    return conteoPorIdioma;
  }
}
