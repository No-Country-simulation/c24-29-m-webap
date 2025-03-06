package com.no_country.fichaje.datos.colaboradores;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record DtoRegColab(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @Positive(message = "El DNI debe ser un número positivo")
        int dni,
        @NotBlank(message = "La dirección es obligatoria")
        String direccion,
        @NotBlank(message = "El código postal es obligatorio")
        String codigoPostal,
        @NotBlank(message = "El teléfono es obligatorio")
        String telefono,
        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no es válido")
        String correoElectronico,
        @NotNull(message = "La fecha de alta es obligatoria")
        Date fechaAlta,
        @NotNull(message = "El estado es obligatorio")
        Estado estado,
        @NotNull(message = "El ID de la organización es obligatorio")
        Long organizacionId,
        @NotBlank(message = "El cargo es obligatorio")
        String cargo,
        @NotBlank(message = "La imagen es obligatoria")
        String frente
) {
}
