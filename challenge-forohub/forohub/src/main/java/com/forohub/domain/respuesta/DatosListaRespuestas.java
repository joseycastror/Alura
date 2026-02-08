package com.forohub.domain.respuesta;

public record DatosListaRespuestas(
    Long id,
    String topico,
    String usuario,
    String mensaje
) {
    public DatosListaRespuestas(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getTopico().getTitulo(), respuesta.getTopico().getUsuario().getNombre(), respuesta.getMensaje());
    }
}
