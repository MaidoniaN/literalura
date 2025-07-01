package com.aluradesafios.literalura.repository;

import com.aluradesafios.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repositorio Spring Data JPA para la entidad Autor, con operaciones CRUD ya implementadas
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Busca un autor por nombre (sin distinguir mayúsculas/minúsculas) y año de nacimiento
    Optional<Autor> findByNombreIgnoreCaseAndAnnoNac(String nombre, Integer annoNac);

    // Busca autores cuyo año de muerte sea menor o igual al especificado (para filtrar autores vivos hasta un año)
    List<Autor> findByAnnoMuerteLessThanEqual(Integer fechNac);
}