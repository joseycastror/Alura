package com.forohub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Usuario findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
