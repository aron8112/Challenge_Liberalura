package com.challenge.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(
    int anioNacimiento,
    int anioMuerte,
    String nombre
) {
}
