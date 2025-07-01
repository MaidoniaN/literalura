package com.aluradesafios.literalura.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "datos_libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // Identificador único del libro en la base de datos
    private String titulo; // Título del libro
    private CategoriaIdioma idioma; // Idioma del libro, usando el enum CategoriaIdioma
    private Integer descargas; // Cantidad de descargas del libro

    @ManyToOne
    @JoinColumn(name = "datos_autor_id") // Define la columna de unión para la relación ManyToOne con Autor
    private Autor autor; // Autor del libro (relación ManyToOne)

    // Constructor por defecto requerido por JPA
    public Libro() {
    }

    // Constructor personalizado que recibe un DatosLibro (record) y asigna los campos correspondientes
    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.idioma = CategoriaIdioma.fromString(datos.idioma().getFirst()); // Convierte el código de idioma a enum
        this.descargas = datos.descargas();
    }

    // Getter y Setter para titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter y Setter para idioma
    public CategoriaIdioma getIdioma() {
        return idioma;
    }

    public void setIdioma(CategoriaIdioma idioma) {
        this.idioma = idioma;
    }

    // Getter y Setter para descargas
    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    // Getter y Setter para autor
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        // Devuelve una representación en String del libro con su título, autor, idioma y descargas
        return "\n>>>>>>>>>> Libro <<<<<<<<<<" +
                "\nTitulo='" + titulo + "'" +
                "\nAutor='" + autor.getNombre() + "'" +
                "\nIdioma='" + idioma + "'" +
                "\nDescargas=" + descargas;
    }
}