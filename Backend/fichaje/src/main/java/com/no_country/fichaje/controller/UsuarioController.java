package com.no_country.fichaje.controller;

import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.Usuario.LoginUsuarioDTO;
import com.no_country.fichaje.datos.Usuario.RegistroUsuarioDTO;
import com.no_country.fichaje.datos.Usuario.Usuario;
import com.no_country.fichaje.infra.security.AutenticacionService;
import com.no_country.fichaje.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.no_country.fichaje.infra.security.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutenticacionService autenticacionService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid RegistroUsuarioDTO usuario){
        try{
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Usuario creado con éxito" + nuevoUsuario));
        }catch (ValidacionExeption e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> loginUsuario(@RequestBody @Valid LoginUsuarioDTO usuario){
       try{
           UserDetails userDetails = autenticacionService.autenticar(usuario.email(), usuario.contrasena());
           Usuario usuario1 = usuarioService.buscarPorEmail(usuario.email());
           String token = tokenService.generarToken(usuario1);
           return ResponseEntity.ok(token);
       } catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body("Error: " + e.getMessage());
       }
    }
}
