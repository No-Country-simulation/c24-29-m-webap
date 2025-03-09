package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.dto.ActualizarColaboradorDTO;
import com.no_country.fichaje.datos.dto.DtoRegColab;
import com.no_country.fichaje.datos.model.Colaboradores;
import com.no_country.fichaje.datos.model.Estado;
import com.no_country.fichaje.datos.model.Organizacion;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    public Colaboradores buscarPorId(Long id) {
        return colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));
    }

    public void actualizarImagen(Long id, String imagen) {
        Colaboradores colaborador = buscarPorId(id);
        colaborador.setFrente(imagen);
        colaboradorRepository.save(colaborador);
    }

    public List<Colaboradores> listarPorOrganizacion(Long organizacionId) {
        return colaboradorRepository.findByOrganizacionId(organizacionId);
    }

    public Colaboradores actualizarDatosPersonales(Long id, ActualizarColaboradorDTO dto) {
        Colaboradores colaborador = buscarPorId(id);

        if (dto.nombre() != null) {
            colaborador.setNombre(dto.nombre());
        }

        if (dto.direccion() != null) {
            colaborador.setDireccion(dto.direccion());
        }

        if (dto.codigoPostal() != null) {
            colaborador.setCcodigoPostal(dto.codigoPostal());
        }

        if (dto.telefono() != null) {
            colaborador.setTelefono(dto.telefono());
        }

        if (dto.correoElectronico() != null) {
            colaborador.setCorreoElectronico(dto.correoElectronico());
        }

        return colaboradorRepository.save(colaborador);
    }

    public Colaboradores actualizarEstado(Long id, Estado estado, String razonBaja, Date fechaBaja) {
        Colaboradores colaborador = buscarPorId(id);
        colaborador.setEstado(estado);

        if (estado == Estado.BAJA) {
            colaborador.setFechaBaja(fechaBaja);
            colaborador.setRazonBaja(razonBaja);
        }

        return colaboradorRepository.save(colaborador);
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

    public List<Organizacion> listarOrganizacionesPorUsuario(Long usuarioId) {
        return organizacionRepository.findByUsuarioId(usuarioId);
    }
}
