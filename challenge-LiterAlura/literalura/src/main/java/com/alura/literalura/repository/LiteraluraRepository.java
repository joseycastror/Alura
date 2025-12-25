package com.alura.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;

public interface LiteraluraRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    @Query("SELECT a FROM Autor a")
    List<Autor> findAllAutores();

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND a.fechaFallecimiento > :fecha")
    List<Autor> findAutoresVivosPorFecha(@Param("fecha") Integer fecha);

    @Query("SELECT b FROM Libro b WHERE b.idioma = :idioma")
    List<Libro> findLibrosPorIdioma(@Param("idioma") Idioma idioma);

    @Query("SELECT a FROM Autor a WHERE a.nombreAutor = :autor")
    Optional<Autor> findAutorPorNombre(@Param("autor") String autor);
}
