package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.Usuario.Usuario;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.colaboradores.DtoRegColab;
import com.no_country.fichaje.datos.organizacion.DtoRegOrg;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.OrganizacionRepository;
import com.no_country.fichaje.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Organizacion regOrg(DtoRegOrg registrar) {
        if (organizacionRepository.existsByNumero(registrar.numeroRegistro())){
            throw new IllegalArgumentException("El numero de organización ya fue registrado");
        }

        Usuario usuario = usuarioRepository.findById(registrar.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Organizacion nuevaOrganizacion = new Organizacion();
        nuevaOrganizacion.setNombre(registrar.nombre());
        nuevaOrganizacion.setNumeroRegistro(registrar.numeroRegistro());
        nuevaOrganizacion.setRazonSocial(registrar.razonSocial());
        nuevaOrganizacion.setRubro(registrar.rubro());
        nuevaOrganizacion.setUsuarioId(usuario);
        return organizacionRepository.save(nuevaOrganizacion);
    }

    public Colaboradores regColaboradores(DtoRegColab registrar){
        if (colaboradorRepository.existsByDni(registrar.dni())){
           throw new IllegalArgumentException("El DNI del Colaborador ya fue registrado anteriormente");
        }

        Organizacion organizacion = organizacionRepository.findById(registrar.organizacionId())
                .orElseThrow(() -> new IllegalArgumentException("La organización con ID " + registrar.organizacionId() + " no existe"));

        Colaboradores colaboradores = new Colaboradores();

        colaboradores.setNombre(registrar.nombre());
        colaboradores.setDni(registrar.dni());
        colaboradores.setDireccion(registrar.direccion());
        colaboradores.setCcodigoPostal(registrar.codigoPostal());
        colaboradores.setTelefono(registrar.telefono());
        colaboradores.setCorreoElectronico(registrar.correoElectronico());
        colaboradores.setFechaAlta(registrar.fechaAlta());
        colaboradores.setCargo(registrar.cargo());
        colaboradores.setEstado(registrar.estado());
        colaboradores.setFrente(registrar.frente());

        colaboradores.setOrganizacion(organizacion);

        return colaboradorRepository.save(colaboradores);
    }
}
