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
}