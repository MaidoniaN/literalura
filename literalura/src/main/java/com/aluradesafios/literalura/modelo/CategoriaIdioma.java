package com.aluradesafios.literalura.modelo;

/**
 * Enum que representa los idiomas soportados en la aplicación LiterAlura.
 * Cada idioma tiene su código API (usado por Gutendex) y su nombre en español.
 */
public enum CategoriaIdioma {
    ES("es", "Español"),
    EN("en", "Inglés"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private String idiomaAPI; // Código del idioma usado en la API Gutendex
    private String idiomaEspanol; // Nombre del idioma en español

    // Constructor del enum
    CategoriaIdioma(String idiomaAPI, String idiomaEspanol) {
        this.idiomaAPI = idiomaAPI;
        this.idiomaEspanol = idiomaEspanol;
    }

    // Método que obtiene la categoría según el código de la API
    public static CategoriaIdioma fromString(String text) {
        for (CategoriaIdioma categoria : CategoriaIdioma.values()) {
            // Compara ignorando mayúsculas o minúsculas
            if (categoria.idiomaAPI.equalsIgnoreCase(text)) {
                return categoria; // Retorna el enum correspondiente si coincide
            }
        }
        // Si no encuentra el idioma, lanza una excepción
        throw new IllegalArgumentException("Categoria NO encontrada: " + text);
    }

    // Método que obtiene la categoría según el nombre en español
    public static CategoriaIdioma fromEspanol(String text) {
        for (CategoriaIdioma categoria : CategoriaIdioma.values()) {
            if (categoria.idiomaEspanol.equalsIgnoreCase(text)) {
                return categoria; // Retorna el enum correspondiente si coincide
            }
        }
        throw new IllegalArgumentException("Categoria NO encontrada: " + text);
    }
}