package com.forohub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
    @NotNull String usuario,
    String mensaje
) {

}
