package com.alura.literalura.model;

import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;
    
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Long descargas;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autor autor;

    // Constructor vacío
    public Libro() {}
    
    // Constructor con parámetros
    public Libro(DatosPorLibro datosPorLibro) {
        this.titulo = datosPorLibro.titulo();
        this.idioma = Idioma.fromString(datosPorLibro.idiomas().stream().limit(1).collect(Collectors.joining()));
        this.autor = new Autor(datosPorLibro.autores().get(0));
        this.descargas = datosPorLibro.descargas();
    }

    // Getter del autor
    public Autor getAutor() {
        return autor;
    }

    // Setter del autor
    public void setAutor(Autor autor) {
    }

    // Getter del título del libro
    public String getTitulo() {
        return titulo;
    }
    
    // Método toString sobreescrito
    @Override
    public String toString() {
        return "\nTítulo: " + this.titulo + "\n" + 
                "Autor: " + this.autor.getNombreAutor() + "\n" +
                "Idioma: " + this.idioma + "\n" +
                "Número de descargas: " + this.descargas + "\n";
    }
}
