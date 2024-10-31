package com.challenge.literalura.repository;

import com.challenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
  Optional<Autor> findByNombre(String nombre);
  @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :year AND (a.anioMuerte IS NULL OR a.anioMuerte >= :year)")
  List<Autor> findAuthorsAliveInYear(int year);

  @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) ORDER BY a.anioNacimiento DESC")
  List<Autor> buscarAutoresPorNombreOrdenadosPorAnioNacimientoDesc(String nombre);

  @Query("SELECT DISTINCT a FROM Autor a JOIN FETCH a.libros l WHERE a.nombre ILIKE %:nombre%")
  Autor findByNombreWithBooks(String nombre);
}
