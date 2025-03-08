package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.dto.AsistenciaDTO;
import com.no_country.fichaje.datos.dto.ReporteAsistenciaDTO;
import com.no_country.fichaje.datos.model.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.service.AsistenciaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.no_country.fichaje.infra.security.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AsistenciasRepository asistenciaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Map<String, Object>> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
       try{
        Optional<Colaboradores> colaboradorOpt = asistenciaService.reconocerColaborador(
                asistenciaDTO.imagenCapturada(), asistenciaDTO.organizacionId());

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
    @GetMapping("/reporte")
    public ResponseEntity<?> obtenerReporte(
            @RequestBody ReporteAsistenciaDTO dto,
            @RequestHeader("Authorization") String token) {
        try {
            Long usuarioId = tokenService.obtenerIdDesdeToken(token);
            List<Map<String, Object>> reporte = asistenciaService.obtenerEstadisticasPorOrganizacion(
                    dto.organizacionId(), dto.periodo());
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasEmpleado(
            @PathVariable Long colaboradorId,
            @RequestParam(required = false, defaultValue = "mes") String periodo) {

        Map<String, Object> estadisticas = asistenciaService.obtenerEstadisticasPorColaborador(colaboradorId, periodo);
        return ResponseEntity.ok(estadisticas);
    }
}
