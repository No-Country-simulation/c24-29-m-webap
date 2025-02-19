package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import com.no_country.fichaje.service.RegistroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/org-reg")
public class RegistroOrgController {

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> regOrganizacion(@RequestBody @Valid DtoRegOrg registrar){
       try{
        Organizacion organizacion = RegistroService.regOrg(registrar);
    }catch (ValidacionExeption e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
       } catch (RuntimeException e){
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
       }
    }
}
