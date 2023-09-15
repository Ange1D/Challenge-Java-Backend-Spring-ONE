package com.ONE.challenge.dto.topico;

import com.ONE.challenge.modelo.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        StatusTopico estado,
        @NotNull
        Long autorId,
        @NotNull
        Long cursoId) {
}

