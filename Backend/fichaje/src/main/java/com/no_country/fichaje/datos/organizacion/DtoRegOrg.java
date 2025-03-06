package com.no_country.fichaje.datos.organizacion;

import jakarta.validation.constraints.NotBlank;


public record DtoRegOrg(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank
        Integer numeroRegistro,
        String razonSocial,
        @NotBlank
        String rubro,
        Long usuarioId
){}