package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.model.Usuario;
import com.no_country.fichaje.datos.dto.DtoRegOrg;
import com.no_country.fichaje.datos.model.Organizacion;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.OrganizacionRepository;
import com.no_country.fichaje.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Organizacion regOrg(DtoRegOrg registrar) {
        if (organizacionRepository.existsByNumeroRegistro(registrar.numeroRegistro())){
            throw new IllegalArgumentException("El numero de organización ya fue registrado");
        }

        Usuario usuario = usuarioRepository.findById(registrar.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("usuario no encontrado"));

        Organizacion nuevaOrganizacion = new Organizacion();
        nuevaOrganizacion.setNombre(registrar.nombre());
        nuevaOrganizacion.setNumeroRegistro(registrar.numeroRegistro());
        nuevaOrganizacion.setRazonSocial(registrar.razonSocial());
        nuevaOrganizacion.setRubro(registrar.rubro());
        nuevaOrganizacion.setUsuario(usuario);
        return organizacionRepository.save(nuevaOrganizacion);
    }
    public void eliminarOrganizacion(Long id) {
        if (!organizacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organización no encontrada");
        }
        organizacionRepository.deleteById(id);
    }
    public Long obtenerIdPorNumeroRegistrado(Integer numeroRegistrado) {
        return organizacionRepository.findByNumeroRegistro(numeroRegistrado)
                .map(Organizacion::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organización no encontrada"));
    }

    public List<Organizacion> listarOrganizacionesPorUsuario(Long usuarioId) {
        return organizacionRepository.findByUsuarioId(usuarioId);
    }
}
