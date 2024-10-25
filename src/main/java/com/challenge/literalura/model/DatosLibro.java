package com.challenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
    @JsonAlias("id") int apiId,
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<DatosAutor> authors,
    @JsonAlias("languages") List<String> lang,
    @JsonAlias("download_count") int descargasNum
) {
}
