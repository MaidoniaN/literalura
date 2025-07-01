package com.aluradesafios.literalura.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "datos_autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // Identificador único del autor en la base de datos
    private String nombre; // Nombre del autor
    private Integer annoNac; // Año de nacimiento del autor
    private Integer annoMuerte; // Año de fallecimiento del autor

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();
    // Lista de libros asociados a este autor
    // mappedBy indica el campo 'autor' en la clase Libro que referencia a esta entidad
    // CascadeType.ALL permite propagar operaciones (persist, merge, remove) a los libros
    // FetchType.EAGER indica que la lista se carga completa junto con la entidad Autor

    // Constructor por defecto requerido por JPA
    public Autor() {
    }

    // Constructor personalizado para crear un autor con nombre, año de nacimiento y año de fallecimiento
    public Autor(String nombre, Integer annoNac, Integer annoMuerte) {
        this.nombre = nombre;
        this.annoNac = annoNac;
        this.annoMuerte = annoMuerte;
    }

    // Método para añadir un libro al autor
    public void setLibro(Libro libro) {
        libro.setAutor(this); // Asigna este autor al libro antes de añadirlo a la lista
        this.libros.add(libro);
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnnoNac() {
        return annoNac;
    }

    public Integer getAnnoMuerte() {
        return annoMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        // Devuelve un resumen legible del autor con su nombre, años y cantidad de libros
        return "\n>>>>>>>>>> Autor <<<<<<<<<<" +
                "\n Nombre='" + nombre + '\'' +
                "\n Año de Nacimiento =" + annoNac +
                "\n Año de Fallecimiento =" + annoMuerte +
                "\n Libros=" + libros.size();
    }
}