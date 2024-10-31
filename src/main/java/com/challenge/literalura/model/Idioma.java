package com.challenge.literalura.model;

public enum Idioma {
  INGLES("en", "inglés"),
  ESPANOL("es", "español"),
  FRANCES("fr", "francés"),
  ALEMAN("de", "alemán"),
  ITALIANO("it", "italiano"),
  CHINO("zh", "chino"),
  JAPONES("ja", "japonés"),
  RUSO("ru", "ruso"),
  GRIEGO("el", "griego"),
  INGLESANTIGUO("ang", "inglés antiguo || anglosajón");

  private String idiomaOmdb;
  private String idiomaEspanol;

  Idioma (String idiomaOmdb, String idiomaEspanol){
    this.idiomaOmdb = idiomaOmdb;
    this.idiomaEspanol = idiomaEspanol;
  }

  public static Idioma fromString(String text) {
    for (Idioma idioma : Idioma.values()) {
      if (idioma.idiomaOmdb.equalsIgnoreCase(text)) {
        return idioma;
      }
    }
    throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
  }

  public static String getIdiomaOmdb(String text) {
    for (Idioma idioma : Idioma.values()) {
      if (idioma.idiomaOmdb.equalsIgnoreCase(text)) {
        return idioma.idiomaEspanol;
      }
    }
    throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
  }
}
