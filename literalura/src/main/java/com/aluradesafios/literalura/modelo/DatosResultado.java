package com.aluradesafios.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Indica que se ignorarán propiedades desconocidas al deserializar el JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultado(
        // Mapea la propiedad "results" del JSON a una lista de objetos DatosLibro
        @JsonAlias("results") List<DatosLibro> todosLosResultados) {

    // Este record representa la respuesta completa de la API Gutendex,
    // conteniendo la lista de todos los libros encontrados en la búsqueda.
}