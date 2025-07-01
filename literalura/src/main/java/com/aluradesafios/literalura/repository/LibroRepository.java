package com.aluradesafios.literalura.repository;

import com.aluradesafios.literalura.modelo.CategoriaIdioma;
import com.aluradesafios.literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio Spring Data JPA para la entidad Libro, con operaciones CRUD ya implementadas
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Busca libros filtrando por idioma (usando el enum CategoriaIdioma)
    List<Libro> findByIdioma(CategoriaIdioma idiomaBuscado);
}