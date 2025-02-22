package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.organizacion.DtoRegOrg;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.datos.organizacion.Redes;
import com.no_country.fichaje.datos.organizacion.RedesDto;
import com.no_country.fichaje.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistroService {

    @Autowired
    private OrganizacionRepository organizacionRepository;



    public Organizacion regOrg(DtoRegOrg registrar) {
        if (organizacionRepository.existsByNumero(registrar.numero())){
            throw new IllegalArgumentException("El numero de organización ya fue registrado");
        }
        Organizacion nuevaOrganizacion = new Organizacion();
        nuevaOrganizacion.setNombre(registrar.nombre());
        nuevaOrganizacion.setNumero(registrar.numero());
        nuevaOrganizacion.setResponsable(registrar.responsable());
        nuevaOrganizacion.setRubro(registrar.rubro());
        nuevaOrganizacion.setTelefono(registrar.telefono());
        nuevaOrganizacion.setEmail(registrar.email());
        nuevaOrganizacion.setRubro(registrar.rubro());

       // nuevaOrganizacion.setPassword(passwordEncoder.encode(registrar.password()));

        Redes redes = new Redes(
                registrar.redes() != null ? registrar.redes().getInstagram() : null,
                registrar.redes() != null ? registrar.redes().getFacebook() : null,
                registrar.redes() != null ? registrar.redes().getX() : null
        );

        nuevaOrganizacion.setRedes(redes);


        return organizacionRepository.save(nuevaOrganizacion);
    }
}
