package com.aluradesafios.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Anotación que indica que se ignorarán propiedades desconocidas al deserializar JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        // Mapea la propiedad "name" del JSON al campo nombre
        @JsonAlias("name") String nombre,

        // Mapea la propiedad "birth_year" del JSON al campo annoNac
        @JsonAlias("birth_year") Integer annoNac,

        // Mapea la propiedad "death_year" del JSON al campo annoMuerte
        @JsonAlias("death_year") Integer annoMuerte) {
    // Este record representa un autor recibido desde la API Gutendex,
    // conteniendo su nombre, año de nacimiento y año de fallecimiento.
}