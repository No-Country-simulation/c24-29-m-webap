package com.no_country.fichaje.datos.colaboradores;

import com.no_country.fichaje.datos.organizacion.Organizacion;

import java.util.Date;

public record DtoRegColab(
        String nombre,
        int dni,
        String direccion,
        String codigoPostal,
        String telefono,
        String correoElectronico,
        Date fechaAlta,
        String estado,
        Long organizacionId,
        Long sectorId,
        String cargo,
        byte[] frente
) {
}
