package com.no_country.fichaje.datos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioDTO(
        @Email(message = "El correo electrónico debe ser válido")
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String contrasena
) {
    @Override
    public @Email(message = "El correo electrónico debe ser válido") @NotBlank(message = "El correo electrónico no puede estar vacío") String email() {
        return email;
    }

    @Override
    public @NotBlank(message = "La contraseña no puede estar vacía") String contrasena() {
        return contrasena;
    }
}
