package com.challenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
    @JsonAlias("birth_year") int anioNacimiento,
    @JsonAlias("death_year") int anioMuerte,
    @JsonAlias("name") String nombre
) {
}
