package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.organizacion.OrganizacionLoginDTO;
import com.no_country.fichaje.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizacion")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    @PostMapping("/login")
    public ResponseEntity<?> loginOrganizacion(@RequestBody OrganizacionLoginDTO loginDTO){
        try {
            Sesion sesion = organizacionService.iniciarSesionOrganizacion(loginDTO.getNumero(),loginDTO.getPassword());

            return ResponseEntity.ok(sesion);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
