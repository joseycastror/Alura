package com.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
    Long id,
    String topico,
    String usuario,
    LocalDateTime fecha_creacion,
    String mensaje
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getTopico().getTitulo(), respuesta.getUsuario().getNombre(), respuesta.getFecha_creacion(), respuesta.getMensaje());
    }
}
