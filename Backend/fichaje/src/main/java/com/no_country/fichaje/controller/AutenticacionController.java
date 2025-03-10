package com.no_country.fichaje.controller;

import com.no_country.fichaje.datos.dto.LoginUsuarioDTO;
import com.no_country.fichaje.datos.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.no_country.fichaje.infra.security.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTtoken> autenticarUsuario(@RequestBody @Valid LoginUsuarioDTO datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.contrasena());
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
        String jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(jwtToken));
    }
}
