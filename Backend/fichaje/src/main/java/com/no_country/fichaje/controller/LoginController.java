package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.asistencia.AsistenciaDTO;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.service.AsistenciaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Colaboradores")
public class LoginController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AsistenciasRepository asistenciaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Map<String, Object>> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
       try{
        Optional<Colaboradores> colaboradorOpt = asistenciaService.reconocerColaborador(
                asistenciaDTO.getImagenCapturada(), asistenciaDTO.getOrganizacionId());

        if (colaboradorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró coincidencia con ningún colaborador"));
        }

        Colaboradores colaborador = colaboradorOpt.get();

        boolean tieneAsistenciaActiva = asistenciaService.tieneAsistenciaActiva(colaborador);

        if (tieneAsistenciaActiva) {
            asistenciaService.registrarSalida(colaborador);
            return ResponseEntity.ok(Map.of("mensaje", "Salida registrada con éxito"));
        } else {
            asistenciaService.registrarEntrada(colaborador);
            return ResponseEntity.ok(Map.of("mensaje", "Entrada registrada con éxito"));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
       }
    }
}
