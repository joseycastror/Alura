package com.forohub.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Query(value = "SELECT * FROM respuestas r WHERE r.topico_id = :topico_id",
        nativeQuery = true)
    Page<Respuesta> findAllByTopico(@Param("topico_id") Long topico_id, Pageable paginacion);
}
