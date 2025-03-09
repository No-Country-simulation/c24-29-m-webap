package com.no_country.fichaje.controller;

import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.model.Colaboradores;
import com.no_country.fichaje.datos.dto.DtoRegOrg;
import com.no_country.fichaje.datos.model.Organizacion;
import com.no_country.fichaje.infra.security.TokenService;
import com.no_country.fichaje.service.AsistenciaService;
import com.no_country.fichaje.service.ColaboradorService;
import com.no_country.fichaje.service.OrganizacionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organizacion")
public class OrganizacionController {

    @Autowired
    public OrganizacionService organizacionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private AsistenciaService asistenciaService;


    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, String>> regOrganizacion(@RequestBody @Valid DtoRegOrg registrar) {
        try {
            Organizacion organizacion = organizacionService.regOrg(registrar);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Organización registrada exitosamente"));
        } catch (ValidacionExeption e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrio un error inesperado" + e.getMessage()));
        }
    }

    @GetMapping("/{id}/colaboradores")
    public ResponseEntity<?> listarColaboradores(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            Long usuarioId = tokenService.obtenerIdDesdeToken(token);
            List<Colaboradores> colaboradores = colaboradorService.listarPorOrganizacion(id);
            return ResponseEntity.ok(colaboradores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<?> obtenerEstadisticas(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "mes") String periodo,
            @RequestHeader("Authorization") String token) {
        try {
            Long usuarioId = tokenService.obtenerIdDesdeToken(token);
            List<Map<String, Object>> estadisticas = asistenciaService.obtenerEstadisticasPorOrganizacion(id, periodo);
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/organizaciones/{id}")
    public ResponseEntity<Void> eliminarOrganizacion(@PathVariable Long id) {
        organizacionService.eliminarOrganizacion(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/organizaciones/buscar")
    public ResponseEntity<Long> obtenerIdPorNumeroRegistrado(@RequestParam Integer numeroRegistro) {
        Long id = organizacionService.obtenerIdPorNumeroRegistrado(numeroRegistro);
        return ResponseEntity.ok(id);
    }
}
