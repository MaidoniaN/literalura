package com.aluradesafios.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Indica que se ignorarán propiedades desconocidas al deserializar el JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        // Mapea la propiedad "title" del JSON al campo titulo
        @JsonAlias("title") String titulo,

        // Mapea la propiedad "languages" del JSON a una lista de idiomas
        @JsonAlias("languages") List<String> idioma,

        // Mapea la propiedad "download_count" del JSON al campo descargas
        @JsonAlias("download_count") Integer descargas,

        // Mapea la propiedad "authors" del JSON a una lista de objetos DatosAutor
        @JsonAlias("authors") List<DatosAutor> autores) {

    // Este record representa un libro recibido desde la API Gutendex,
    // incluyendo su título, idiomas, cantidad de descargas y lista de autores asociados.
}