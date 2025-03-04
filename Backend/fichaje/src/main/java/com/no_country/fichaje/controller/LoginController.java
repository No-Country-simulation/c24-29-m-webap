package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.asistencia.AsistenciaDTO;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.organizacion.OrganizacionLoginDTO;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.SesionRepository;
import com.no_country.fichaje.service.AsistenciaService;
import com.no_country.fichaje.service.OrganizacionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private OrganizacionService organizacionService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private AsistenciasRepository asistenciaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @PostMapping("/organization/login")
    @Transactional
    @ResponseStatus
    public ResponseEntity<?> loginOrganizacion(@RequestBody OrganizacionLoginDTO loginDTO){
        try {
            Sesion sesion = organizacionService.iniciarSesionOrganizacion(loginDTO.getNumero(),loginDTO.getPassword());

            return ResponseEntity.ok(sesion);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/colaboradores/login")
    @Transactional
    @ResponseStatus
    public ResponseEntity<Map<String, Object>> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
        try {
            Optional<Sesion> orgSessionOpt = asistenciaService.validarSesionOrganizacion(
                    asistenciaDTO.getOrganizacionId(), asistenciaDTO.getSesionKey());
            if (orgSessionOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "Sesión de organización inválida o expirada"));
            }
            Sesion orgSession = orgSessionOpt.get();

            Optional<Colaboradores> colaboradorOpt = asistenciaService.reconocerColaborador(
                    asistenciaDTO.getImagenCapturada(), asistenciaDTO.getOrganizacionId());
            if (colaboradorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No se encontró coincidencia con ningún colaborador"));
            }
            Colaboradores colaborador = colaboradorOpt.get();

            boolean tieneAsistenciaActiva = asistenciaService.tieneAsistenciaActiva(colaborador, orgSession);
            if (tieneAsistenciaActiva) {
                asistenciaService.registrarSalida(colaborador, orgSession);
                return ResponseEntity.ok(Map.of("mensaje", "Salida registrada con éxito"));
            } else {
                asistenciaService.registrarEntrada(colaborador, orgSession);
                return ResponseEntity.ok(Map.of("mensaje", "Entrada registrada con éxito"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
