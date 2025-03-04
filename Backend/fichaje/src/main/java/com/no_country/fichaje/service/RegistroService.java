package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.colaboradores.DtoRegColab;
import com.no_country.fichaje.datos.colaboradores.Estado;
import com.no_country.fichaje.datos.organizacion.DtoRegOrg;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.datos.organizacion.Redes;
import com.no_country.fichaje.datos.organizacion.RedesDto;
import com.no_country.fichaje.datos.sectores.Sectores;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.OrganizacionRepository;
import com.no_country.fichaje.repository.SectoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SectoresRepository sectoresRepository;



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
                registrar.redes() != null ? registrar.redes().getTwitter() : null
        );

        nuevaOrganizacion.setRedes(redes);


        return organizacionRepository.save(nuevaOrganizacion);
    }

    public Colaboradores regColaboradores(DtoRegColab registrar){
        if (colaboradorRepository.existsByDni(registrar.dni())){
           throw new IllegalArgumentException("El DNI del Colaborador ya fue registrado anteriormente");
        }
        Estado estado = Estado.valueOf(registrar.estado().toUpperCase());
        Colaboradores colaboradores = new Colaboradores();

        colaboradores.setNombre(registrar.nombre());
        colaboradores.setDni(registrar.dni());
        colaboradores.setDireccion(registrar.direccion());
        colaboradores.setCodigo_postal(registrar.codigoPostal());
        colaboradores.setTelefono(registrar.telefono());
        colaboradores.setCorreoElectronico(registrar.correoElectronico());
        colaboradores.setFechaAlta(registrar.fechaAlta());
        colaboradores.setCargo(registrar.cargo());
        colaboradores.setEstado((estado));
        colaboradores.setFrente(registrar.frente());

        Organizacion organizacion = new Organizacion();
        organizacion = organizacionRepository.getReferenceById(registrar.organizacionId());
        colaboradores.setOrganizacion(organizacion);

        Sectores sector = new Sectores();
        sector = sectoresRepository.getReferenceById(registrar.sectorId());
        colaboradores.setSector(sector);

        return colaboradorRepository.save(colaboradores);
    }
}
