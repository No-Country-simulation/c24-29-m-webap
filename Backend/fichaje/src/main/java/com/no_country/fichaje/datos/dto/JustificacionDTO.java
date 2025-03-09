package com.no_country.fichaje.datos.dto;

import jakarta.validation.constraints.NotBlank;

public record JustificacionDTO(
        @NotBlank(message = "La justificación es obligatoria")
        String justificacion) {
}
