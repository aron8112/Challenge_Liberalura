package com.challenge.literalura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="libros")
public class Libro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int apiId;
  private String title;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "autor_id")
  private Autor autor;
  private String lang;
  private int descargasNum;

  public Libro(){}
  public Libro(DatosLibro datosLibro) {
    this.apiId = datosLibro.apiId();
    this.title = datosLibro.title();
    this.lang = datosLibro.lang().isEmpty() ? "desconocido" : datosLibro.lang().get(0);
    this.descargasNum = datosLibro.descargasNum();
    this.autor = new Autor(datosLibro.authors().get(0));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getApiId() {
    return apiId;
  }

  public void setApiId(int apiId) {
    this.apiId = apiId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public int getDescargasNum() {
    return descargasNum;
  }

  public void setDescargasNum(int descargasNum) {
    this.descargasNum = descargasNum;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
    if (autor != null && !autor.getLibros().contains(this)) {
      autor.getLibros().add(this);
    }
  }

  @Override
  public String toString() {
    return
        "\n======================================\n" +
            "Título= " + title + ",\n" +
            "Gutendex Id=" + apiId + ",\n" +
            "Autor=" + autor.getNombre() + ",\n" +
            "Idioma=" + lang + ",\n" +
            "Cantidad de descargas=" + descargasNum +
            "\n======================================\n";
  }
}
