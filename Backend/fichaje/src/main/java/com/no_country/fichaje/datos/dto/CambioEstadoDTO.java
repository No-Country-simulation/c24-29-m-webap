package com.no_country.fichaje.datos.dto;
import com.no_country.fichaje.datos.model.Estado;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record CambioEstadoDTO(
        @NotNull(message = "El estado es obligatorio")
        Estado estado,
        String razonBaja,
        Date fechaBaja) {
}
