package com.challenge.literalura.service;

import com.challenge.literalura.dto.AutorDTO;
import com.challenge.literalura.model.Autor;
import com.challenge.literalura.model.DatosAutor;
import com.challenge.literalura.model.Libro;
import com.challenge.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

  private final AutorRepository autorRepository;

  @Autowired
  public AutorService(AutorRepository autorRepository) {
    this.autorRepository = autorRepository;
  }

  // Método para buscar o crear un autor a partir de DatosAutor
  public Autor obtenerOCrearAutor(DatosAutor datosAutor) {
    if (datosAutor == null){
      Optional<Autor> autorDesconocido = autorRepository.findById(999L);
      DatosAutor autorDesc = new DatosAutor(0,0,"Unknown");

      return autorDesconocido.orElseGet(() -> autorRepository.save(new Autor(autorDesc)));
    } else {
    Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());
    // Si el autor existe, lo retorna; si no, crea y guarda uno nuevo
    return autorExistente.orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

    }
  }

  public List<AutorDTO> findAll() {
    return convierteDatos(autorRepository.findAll());
  }

  public List<AutorDTO> buscarAutoresPorAnio(int anioBuscado) {
    return convierteDatos(autorRepository.findAuthorsAliveInYear(anioBuscado));
  }

  public List<AutorDTO> buscarAutoresPorNombreOrdenados(String nombre) {
    return convierteDatos(autorRepository.buscarAutoresPorNombreOrdenadosPorAnioNacimientoDesc(nombre));
  }

  public List<AutorDTO> convierteDatos(List<Autor> autor){
    return autor.stream()
        .map(s-> new AutorDTO(s.getAnioNacimiento(),s.getAnioMuerte(),s.getNombre()))
        .collect(Collectors.toList());
  }

  public Map<AutorDTO, List<String>> buscarAutoresTitulos(String nombre){
    Autor autor = autorRepository.findByNombreWithBooks(nombre);
    if (autor == null) {
      return null;
    }
    System.out.println(autor);
    AutorDTO autorDto = new AutorDTO(autor.getAnioNacimiento(), autor.getAnioMuerte(), autor.getNombre());
    Map<AutorDTO, List<String>> autoresConTitulos = new HashMap<>();

      List<String> titulos = autor.getLibros().stream()
          .map(l-> String.valueOf(l.getTitle())+"\n")
          .collect(Collectors.toList());
      autoresConTitulos.put(autorDto, titulos);

    return autoresConTitulos;
  }
}
