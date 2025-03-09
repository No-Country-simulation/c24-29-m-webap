package com.no_country.fichaje.datos.dto;

import jakarta.validation.constraints.NotNull;

public record ReporteAsistenciaDTO(
        @NotNull(message = "El ID de la organización es obligatorio")
        Long organizacionId,
        @NotNull(message = "El periodo es obligatorio")
        String periodo
) {
}
