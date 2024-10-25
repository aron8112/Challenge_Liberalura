package com.challenge.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int anioNacimiento;
  private int anioMuerte;
  private String nombre;
  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Libro> libros = new ArrayList<>();

  public Autor(){}
  public Autor(DatosAutor datosAutor) {
  this.anioMuerte = datosAutor.anioMuerte();
  this.anioNacimiento = datosAutor.anioNacimiento();
  this.nombre = datosAutor.nombre();
  }

  public int getAnioNacimiento() {
    return anioNacimiento;
  }

  public void setAnioNacimiento(int anioNacimiento) {
    this.anioNacimiento = anioNacimiento;
  }

  public int getAnioMuerte() {
    return anioMuerte;
  }

  public void setAnioMuerte(int anioMuerte) {
    this.anioMuerte = anioMuerte;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setLibro(Libro libro) {
    libros.add(libro);
    libro.setAutor(this); // Asegura que el autor del libro sea este objeto Autor
  }

  public List<Libro> getLibros() {
    return libros;
  }

  @Override
  public String toString() {
    return "Autor{" +
        "anioNacimiento=" + anioNacimiento +
        ", anioMuerte=" + anioMuerte +
        ", nombre='" + nombre + '\'' +
        ", libros=" + libros +
        '}';
  }
}
