package com.forohub.domain.topico;

public record DatosListaTopico(
    Long id,
    String titulo,
    String usuario,
    String curso
) {
    public DatosListaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getUsuario().getNombre(), topico.getCurso());
    }
}
