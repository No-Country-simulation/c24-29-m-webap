package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.asistencia.AsistenciaDTO;
import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.asistencia.CompareDTO;
import com.no_country.fichaje.service.AsistenciaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

  @PostMapping
  @Transactional
    public ResponseEntity<?> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO){
        try{
            Asistencias asistencias = asistenciaService.reconocimiento(
                    asistenciaDTO.getSesionKey(),
                    asistenciaDTO.getColaboradorId(),
                    asistenciaDTO.getImagenCapturada()
            );
            return ResponseEntity.ok(asistencias);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
