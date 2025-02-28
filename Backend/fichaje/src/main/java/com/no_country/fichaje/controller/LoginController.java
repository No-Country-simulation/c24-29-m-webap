package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.asistencia.AsistenciaDTO;
import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.organizacion.OrganizacionLoginDTO;
import com.no_country.fichaje.service.AsistenciaService;
import com.no_country.fichaje.service.OrganizacionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private OrganizacionService organizacionService;

    @Autowired
    private AsistenciaService asistenciaService;

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
    public ResponseEntity<?> loginColaboradores(@RequestBody AsistenciaDTO asistenciaDTO){
        try {
            Mono sesion = asistenciaService.reconocimiento(asistenciaDTO.getImagenCapturada());

            return ResponseEntity.ok(sesion);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
