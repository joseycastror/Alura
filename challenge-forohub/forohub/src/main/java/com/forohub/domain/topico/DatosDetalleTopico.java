package com.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
    Long id,
    String titulo,
    LocalDateTime fecha_creacion,
    String usuario,
    String curso,
    String mensaje
) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getFecha_creacion(), topico.getUsuario().getNombre(), topico.getCurso(), topico.getMensaje());
    }
}
