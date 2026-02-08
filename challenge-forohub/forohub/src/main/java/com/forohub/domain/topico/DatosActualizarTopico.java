package com.forohub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
    @NotNull Long id,
    @NotNull String usuario,
    String titulo,
    String mensaje
) {

}
