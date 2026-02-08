package com.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
    @NotNull String usuario,
    @NotBlank String titulo,
    @NotBlank String mensaje,
    @NotBlank String curso
) {

}
