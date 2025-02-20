package com.no_country.fichaje.controller;

import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.organizacion.DtoRegOrg;
import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.service.RegistroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/org-reg")
public class RegistroOrgController {

    @Autowired
    public RegistroService registroService;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> regOrganizacion(@RequestBody @Valid DtoRegOrg registrar) {
        try {
            Organizacion organizacion = registroService.regOrg(registrar);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Organización registrada exitosamente"));
        } catch (ValidacionExeption e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Ocurrio un error inesperado" + e.getMessage()));
        }
    }
}
