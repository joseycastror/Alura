package com.alura.literalura.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alura.literalura.model.DatosConsulta;
import com.alura.literalura.model.DatosPorLibro;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.LiteraluraRepository;
import com.alura.literalura.services.ConvertirDatos;
import com.alura.literalura.services.RequestAPI;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private RequestAPI requestAPI = new RequestAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvertirDatos conversion = new ConvertirDatos();
    private LiteraluraRepository repositorio;

    public void MuestraElMenu(){
        var opcion = -1;
        
        // Bucle del menú
        while(opcion != 0) {
            System.out.println("""
                ==========================================
                LiterAlura - Libros en Project Gutenberg 
                ==========================================
                ¿Qué desea hacer?
                
                1. Buscar libro por título en Gutendex
                2. Mostrar los libros registrados en la Base de Datos
                3. Mostrar los autores en la Base de Datos
                4. Mostrar los autores vivos en un año específico
                5. Mostrar libros en la Base de Datos por idioma

                0. Salir
                """);

            // Tratamiento para excepciones cuando la entrada del usuario no es un número entero
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
                
            } catch(Exception e) {
                System.out.println("No existe la opción en el menú.");
                System.out.println("Por favor ingrese una opción válida.");
            }
            
            // Menu principal
            switch(opcion) {
                case 1:
                    try {
                        buscarLibroWeb();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } 
                    break;

                case 2:
                    getLibrosBD();
                    break;

                case 3:
                    getAutoresBD();
                    break;

                case 4:
                    getAutoresVivosPorFecha();
                    break;

                case 5:
                    getLibrosPorIdiomaBD();
                    break;
                
                default:
                    break;
            }
        }
    }

    // Método para obtener todos los datos de la búsqueda en Gutendex
    private DatosConsulta getDatosBusqueda() {
        // Implementar try catch aquí para lidiar con JSON null
        System.out.println("Escriba el nombre del libro que desea buscar:");
        var nombreLibro = teclado.nextLine();
        var json = requestAPI.obtenerDatos(URL_BASE + nombreLibro.toLowerCase().replace(" ", "%20"));

        DatosConsulta datos = conversion.obtenerDatos(json, DatosConsulta.class);
        return datos;
    }   

    // Método del Case 1
    private void buscarLibroWeb() {
        DatosConsulta datos = getDatosBusqueda();
        
        Optional<DatosPorLibro> libroBusqueda = datos.libros().stream().findFirst();
        List<Libro> librosEncontrados = libroBusqueda.stream().map(e -> new Libro(e)).collect(Collectors.toList());
        // Considerar crear un menú para elegir entre los 5 primeros resultados
        
        var primerLibro = librosEncontrados.get(0);
        var libroEnBD = repositorio.findByTituloContainsIgnoreCase(primerLibro.getTitulo());

        // CAMBIAR: no inserta un nuevo libro si el autor ya existe
        if(libroEnBD.isPresent()) {
            System.out.println("El libro " + primerLibro.getTitulo() + " ya existe en tu base de datos");
        } else {
            String primerAutor = librosEncontrados.get(0).getAutor().getNombreAutor();
            var autorEnBD = repositorio.findAutorPorNombre(primerAutor);

            if (autorEnBD.isPresent()) {
                System.out.println("Ya tienes un libro registrado de " + primerAutor);
                System.out.println("Por el momento solo puede registrarse un libro por autor");
            } else {
                System.out.println(primerLibro.toString());
                repositorio.save(primerLibro);
            }
        }
    }

    // Método del Case 2
    private void getLibrosBD() {
        List<Libro> libros = repositorio.findAll();
        for(Integer i = 0; i < libros.size(); i++) {
            System.out.println((i + 1) + ".- " + libros.get(i).getTitulo() + 
                                " - " + libros.get(i).getAutor().getNombreAutor());
        }
    }

    // Método del Case 3
    private void getAutoresBD() {
        List<Autor> autores = repositorio.findAllAutores();
        
        for(Integer i = 0; i < autores.size(); i++) {
            System.out.println((i + 1) + ".- " + autores.get(i).getNombreAutor() + 
                                " - (" + autores.get(i).getFechaNacimiento() + " - " +
                                autores.get(i).getFechaFallecimiento() + ")");
        }
    }

    // Método del Case 4
    private void getAutoresVivosPorFecha() {
        System.out.println("Ingrese el año que desea revisar:");
        try {
            Integer fecha = Integer.parseInt(teclado.nextLine());
            List<Autor> autoresVivos = repositorio.findAutoresVivosPorFecha(fecha);

            for(Integer i = 0; i < autoresVivos.size(); i++) {
            System.out.println((i + 1) + ".- " + autoresVivos.get(i).getNombreAutor());
            }
        } catch(Exception e) {
            System.out.println("Debe ingresar un año válido");
        }
    }

    // Método del Case 5
    private void getLibrosPorIdiomaBD() {
        System.out.println("Ingrese el código del idioma que desea buscar:");
        System.out.println("""
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugés
                de - Alemán
                """);
        
        try {
            Idioma idioma = Idioma.valueOf(teclado.nextLine().toUpperCase());
            List<Libro> librosEnElIdioma = repositorio.findLibrosPorIdioma(idioma);

            for(Integer i = 0; i < librosEnElIdioma.size(); i++) {
                System.out.println((i + 1) + ".- " + librosEnElIdioma.get(i).getTitulo() +
                                    " - " + librosEnElIdioma.get(i).getAutor().getNombreAutor());
            }
        } catch(Exception e) {
            System.out.println("El idioma es inválido o no hay libros en el idioma indicado.");
            System.out.println("Intente de nuevo.");
        }
    }

    // Constructor
    public Principal(LiteraluraRepository repository) {
        this.repositorio = repository;
    }
}
