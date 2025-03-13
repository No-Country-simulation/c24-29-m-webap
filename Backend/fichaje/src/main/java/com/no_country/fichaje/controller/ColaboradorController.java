package com.no_country.fichaje.controller;

import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.dto.*;
import com.no_country.fichaje.datos.model.Asistencias;
import com.no_country.fichaje.datos.model.Colaboradores;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private OrganizacionService organizacionService;
    
    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, String>> regColab( @RequestHeader("Authorization") String token, @RequestBody @Valid DtoRegColab regColab){
        try {
            tokenService.validarToken(token);
            Colaboradores colaboradores = colaboradorService.regColaboradores(regColab);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Colaborador Registrado Exitosamente"));
        }catch (ValidacionExeption e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrio un error inesperado" + e.getMessage()));
        }
    }
    @PutMapping("/{id}/imagen")
    public ResponseEntity<Map<String, Object>> actualizarImagen(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody ImagenDto imagen) {
        try {
            // Validar el token (si aplica)
            tokenService.validarToken(token);

            // Llamada al servicio con el valor correcto
            colaboradorService.actualizarImagen(id, imagen.imagen());

            // Respuesta exitosa
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Imagen actualizada correctamente",
                    "idColaborador", id));
        } catch (IllegalArgumentException e) {
            // Datos inválidos o colaborador no encontrado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (ResponseStatusException e) {
            // Excepciones personalizadas del servicio
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getReason()));
        } catch (Exception e) {
            // Excepciones generales no esperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ha ocurrido un error inesperado"));
        }
    }
    @GetMapping("/{id}/asistencias")
    public ResponseEntity<?> getAsistencias(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String periodo) {
        try {
            tokenService.validarToken(token);
            List<Asistencias> asistencias = asistenciaService.obtenerAsistencias(id, periodo);
            return ResponseEntity.ok(asistencias);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatosPersonales(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ActualizarColaboradorDTO dto) {
        try {
            tokenService.validarToken(token);
            Colaboradores colaborador = colaboradorService.actualizarDatosPersonales(id, dto);
            return ResponseEntity.ok(colaborador);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestBody @Valid CambioEstadoDTO dto,
            @RequestHeader("Authorization") String token) {
        try {
            tokenService.validarToken(token);
            Long usuarioId = tokenService.obtenerIdDesdeToken(token);
            Colaboradores colaborador = colaboradorService.actualizarEstado(id, dto.estado(), dto.razonBaja(), dto.fechaBaja());
            return ResponseEntity.ok(colaborador);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/{id}/justificar/{asistenciaId}")
    public ResponseEntity<?> justificarInasistencia(
            @PathVariable Long id,
            @PathVariable Long asistenciaId,
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid JustificacionDTO dto) {
        try {
            tokenService.validarToken(token);
            asistenciaService.registrarJustificacion(asistenciaId, dto.justificacion());
            return ResponseEntity.ok(Map.of("mensaje", "Justificación registrada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
