package com.no_country.fichaje.datos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @Email(message = "El correo electrónico no es válido")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena
) {
}
