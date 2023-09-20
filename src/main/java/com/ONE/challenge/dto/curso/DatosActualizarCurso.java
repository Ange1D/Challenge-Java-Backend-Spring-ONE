package com.ONE.challenge.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotBlank
        String nombre,
        @NotNull
        String categoria
) {
}
