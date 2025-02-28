package com.no_country.fichaje.datos.organizacion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoRegOrg(
        @NotBlank
        String nombre,
        int numero,
        String siglasOrg,
        @NotBlank
        String responsable,
        String rubro,
        @Pattern(regexp = "\\d{10,15}", message = "El teléfono debe contener entre 10 y 15 dígitos")
        String telefono,
        @Email
        String email,
        @NotBlank String password,
        Redes redes){}