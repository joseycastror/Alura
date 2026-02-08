package com.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroRespuesta(
    @NotBlank String usuario,
    //@NotBlank Long topico_id,
    @NotBlank String mensaje
) {
}
