package com.no_country.fichaje.datos.dto;

public record ActualizarColaboradorDTO(
        String nombre,
        String direccion,
        String codigoPostal,
        String telefono,
        String correoElectronico) {
}
