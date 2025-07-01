package com.aluradesafios.literalura;

import com.aluradesafios.literalura.principal.Principal;
import com.aluradesafios.literalura.repository.AutorRepository;
import com.aluradesafios.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Marca esta clase como la principal de Spring Boot
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository; // Inyección automática del repositorio LibroRepository

    @Autowired
    private AutorRepository autorRepository; // Inyección automática del repositorio AutorRepository

    public static void main(String[] args) {
        // Punto de entrada principal para iniciar la aplicación Spring Boot
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    // Método que se ejecuta después de que la aplicación Spring Boot arranca
    @Override
    public void run(String... args) throws Exception {
        // Crea instancia de la clase Principal, inyectando los repositorios para usar en consola
        Principal principal = new Principal(libroRepository, autorRepository);
        principal.mostrarMenu(); // Inicia el menú interactivo por consola
    }
}