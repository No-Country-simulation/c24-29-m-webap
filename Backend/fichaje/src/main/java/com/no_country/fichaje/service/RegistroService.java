package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.organizacion.DtoRegOrg;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistroService {

    @Autowired
    private OrganizacionRepository organizacionRepository;


    public static Organizacion regOrg(DtoRegOrg registrar) {
        if (organizacionRepository.existsByNumero(registrar.numero()){
            throw new IllegalArgumentException("El numero de organización ya fue registrado");
        }
        Organizacion nuevaOrganizacion = new Organizacion();
        nuevaOrganizacion.setNombre(registrar.nombre());
        nuevaOrganizacion.setNumero(registrar.numero());
        nuevaOrganizacion.setResponsable(registrar.responsable());
        nuevaOrganizacion.setRubro(registrar.rubro());
        return null;
    }
}
