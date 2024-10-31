package com.challenge.literalura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResAPI(
    @JsonAlias("count") int contador,
    @JsonAlias("results") ArrayList<DatosLibro> results,
    @JsonAlias("next") String nextResult,
    @JsonAlias("previous") String previousResult
) {
}
