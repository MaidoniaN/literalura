package com.aluradesafios.literalura.principal;

import com.aluradesafios.literalura.modelo.*;
import com.aluradesafios.literalura.repository.AutorRepository;
import com.aluradesafios.literalura.repository.LibroRepository;
import com.aluradesafios.literalura.service.ConsumoAPI;
import com.aluradesafios.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in); // Para capturar entradas del usuario por consola
    private ConsumoAPI consumoAPI = new ConsumoAPI(); // Servicio para consumir API externa Gutendex
    private ConvierteDatos conversor = new ConvierteDatos(); // Servicio para convertir JSON a objetos Java
    private final String URL_BASE = "https://gutendex.com/books/"; // URL base para búsqueda de libros en Gutendex
    private String libroBuscado; // Variable para almacenar el título del libro buscado
    List<Libro> libros; // Lista para guardar libros consultados de la BD

    LibroRepository libroRepository; // Repositorio para acceso a datos de libros
    AutorRepository autorRepository; // Repositorio para acceso a datos de autores

    // Constructor que recibe los repositorios para su uso en esta clase
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    // Método principal que muestra el menú y controla el flujo del programa
    public void mostrarMenu() {
        var opcion = -1; // Variable para almacenar opción ingresada por usuario

        // Menú principal que se muestra en consola
        var menu_ppal = """
                \n
                >>>>>>>> BIENVENIDO AL DESAFIO LITERALURA <<<<<<<<
                
                1 - Buscar un Libro por Titulo
                2 - Listar Libros Registrados 
                3 - Listar Autores Registrados
                4 - Listar Autores Vivos en un Determinado Año
                5 - Listar Libros por Idioma
                
                0 -  Salir
                """;

        // Menú para pedir título del libro
        var menuLibro = """
                \n
                >>>>>>>> MENU OPCION 1 - Buscar un Libro por Titulo <<<<<<<<
                Ingrese nombre del libro: 
                """;

        // Menú para seleccionar idioma en listado por idioma
        var menuIdiomas = """
                >>>>>>>> MENU OPCION 5 - Listar Libros Por Idioma <<<<<<<<
                Opción  -> Idioma
                   es   -> Español
                   en   -> Inglés
                   fr   -> Francés
                   pt   -> Portugués
                
                Seleccione una Opcion
                """;

        // Bucle que se mantiene hasta que el usuario seleccione salir (0)
        while (opcion != 0) {
            try {
                System.out.println(menu_ppal); // Muestra menú principal
                opcion = Integer.valueOf(teclado.nextLine()); // Lee opción desde consola
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()); // En caso de error en ingreso de número
            }

            switch (opcion) {
                case 1:
                    almacenarBusqueda(); // Busca y guarda libro desde API y BD
                    break;
                case 2:
                    mostrarLibrosRegistrados(); // Muestra todos los libros guardados en BD
                    break;
                case 3:
                    mostrarAutoresRegistrados(); // Muestra todos los autores guardados en BD
                    break;
                case 4:
                    mostrarAutoresVivosAnno(); // Muestra autores vivos en un año dado
                    break;
                case 5:
                    System.out.println(menuIdiomas); // Muestra menú para elegir idioma
                    var idiomaSeleccionado = teclado.nextLine();
                    System.out.println("Listando los libros en: " + idiomaSeleccionado);
                    mostrarLibrosPorIdioma(idiomaSeleccionado); // Lista libros filtrados por idioma
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida"); // Opción no reconocida
                    break;
            }
        }
    }

    // Método para solicitar título al usuario, consumir la API y obtener resultados
    private DatosResultado datosBusqueda() {
        System.out.println("\nIngresa el libro que deseas buscar: ");
        libroBuscado = teclado.nextLine(); // Lee título buscado
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + libroBuscado.replace(" ", "+")); // Consulta API
        DatosResultado librosResultado = conversor.obtenerDatos(json, DatosResultado.class); // Deserializa JSON a objetos
        return librosResultado;
    }

    // Método para almacenar la búsqueda en la BD, si el libro se encuentra en API
    private Libro almacenarBusqueda() {
        var librosEncontrados = datosBusqueda(); // Obtiene resultados de la API
        Optional<DatosLibro> busquedaLibro = librosEncontrados.todosLosResultados().stream()
                .filter(l -> l.titulo().toLowerCase()
                        .contains(libroBuscado.toLowerCase())) // Busca título similar (ignore case)
                .findFirst();

        if (busquedaLibro.isPresent()) {
            DatosLibro datos = busquedaLibro.get();
            Libro libro = new Libro(datos); // Crea entidad Libro desde DTO

            // Si hay autores asociados, se procesa el primero para la BD
            if (datos.autores() != null && !datos.autores().isEmpty()) {
                DatosAutor datosAutor = datos.autores().get(0);

                // Busca autor en BD por nombre y año de nacimiento
                Optional<Autor> autorBuscado = autorRepository.findByNombreIgnoreCaseAndAnnoNac(
                        datosAutor.nombre(),
                        datosAutor.annoNac()
                );

                // Si no existe, crea un nuevo autor
                Autor autor = autorBuscado.orElse(
                        new Autor(datosAutor.nombre(), datosAutor.annoNac(), datosAutor.annoMuerte())
                );

                autor.setLibro(libro); // Establece relación entre autor y libro
                autorRepository.save(autor); // Guarda o actualiza autor con libro
            }

            System.out.println("\n" + "=".repeat(30) + "\nLibro Encontrado" + "\n" + "=".repeat(30));
            return libro;

        } else {
            System.out.println("\n" + "=".repeat(30)
                    + "\nLibro NO encontrado" + "\n" + "=".repeat(30));
            return null;
        }
    }

    // Método para mostrar todos los libros almacenados en la base de datos
    private void mostrarLibrosRegistrados() {
        libros = libroRepository.findAll(); // Consulta todos los libros
        libros.forEach(System.out::println); // Imprime cada libro usando toString()
        System.out.println("\n" + "-".repeat(40) + "\nEl total de libros guardados es: " + libros.size() + "\n" + "-".repeat(40));
    }

    // Método para mostrar todos los autores almacenados en la base de datos
    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll(); // Consulta todos los autores
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre)) // Ordena por nombre alfabéticamente
                .forEach(System.out::println); // Imprime cada autor
        System.out.println("\n" + "-".repeat(40) + "\nExisten " + autores.size() + " registrados" + "\n" + "-".repeat(40));
    }

    // Método para listar autores que estaban vivos en un año especificado
    private void mostrarAutoresVivosAnno() {
        System.out.println("\n" + "-".repeat(10) + "LISTAR AUTORES VIVOS EN CIERTO AÑO" + "-".repeat(10));
        System.out.println("\n" + "-".repeat(2) + "Ingrese año para listar: ");
        var annoBuscado = Integer.valueOf(teclado.nextLine()); // Lee año ingresado

        // Consulta autores cuyo año de muerte es mayor o igual al año buscado
        List<Autor> autoresVivosPorAnno = autorRepository.findByAnnoMuerteLessThanEqual(annoBuscado);

        if (!autoresVivosPorAnno.isEmpty()) {
            autoresVivosPorAnno.forEach(System.out::println); // Imprime autores encontrados
            System.out.println("Existen: " + autoresVivosPorAnno.size() + (autoresVivosPorAnno.size() == 1 ? " Autor" : " Autores"));
        } else {
            System.out.println("\n" + "-".repeat(45) + "\nNo existen autores vivos antes de ese año" + "\n" + "-".repeat(45));
        }
    }

    // Método para listar libros filtrados por idioma
    private void mostrarLibrosPorIdioma(String idiomaSeleccionado) {
        System.out.println("Idioma seleccionado: " + idiomaSeleccionado);

        CategoriaIdioma idiomaABuscar = CategoriaIdioma.fromString(idiomaSeleccionado); // Convierte string a enum
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaABuscar); // Consulta libros por idioma

        librosPorIdioma.forEach(System.out::println); // Imprime libros filtrados
        System.out.println("\n" + "-".repeat(45) + "\nExisten " + librosPorIdioma.size() + (librosPorIdioma.size() == 1 ? " libro" : " libros") + "\n" + "-".repeat(45));
    }

}