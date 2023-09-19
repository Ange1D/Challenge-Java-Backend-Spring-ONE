package com.ONE.challenge.dto.usuario;

import com.ONE.challenge.modelo.Tipo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizarUsuario(
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        String contrasena,
        Tipo tipo
) {
}
