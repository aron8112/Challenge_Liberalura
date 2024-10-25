package com.challenge.literalura.repository;

import com.challenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
  @Query("SELECT DISTINCT l.title FROM Libro l")
  List<Libro> findDistinctTitle();

  @Query("SELECT l.lang, COUNT(l) FROM Libro l GROUP BY l.lang")
  List<Object[]> countBooksByLanguage();

}
