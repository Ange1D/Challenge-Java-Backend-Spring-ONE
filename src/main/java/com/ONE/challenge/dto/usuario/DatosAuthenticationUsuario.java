package com.ONE.challenge.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAuthenticationUsuario(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String contrasena) {
}
