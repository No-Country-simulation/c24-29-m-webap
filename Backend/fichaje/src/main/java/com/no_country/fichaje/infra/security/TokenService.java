package com.no_country.fichaje.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.model.Usuario;
import com.no_country.fichaje.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService{

@Value("${api.security.secret}")
private String apiSecret;

@Autowired
private UsuarioRepository usuarioRepository;


public String generarToken(Usuario usuario) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(generarFechaExpiracion()))
                .withIssuer("fichaje")
                .sign(algorithm);
    }catch (JWTCreationException e){
        throw new RuntimeException("Error al generar el token JWT", e);
    }
}

public String getSubject(String token) {
    if (token == null) {
        throw new RuntimeException("Token no puede ser null");
    }
    try {
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        return JWT.require(algorithm)
                .withIssuer("fichaje")
                .build()
                .verify(token)
                .getSubject();
    } catch (JWTVerificationException exception) {
        throw new SecurityException("Token JWT inválido o expirado", exception);
    }
}

private Instant generarFechaExpiracion() {
    return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
}

    public Long obtenerIdDesdeToken(String token) {

        String subject = getSubject(token);
        return usuarioRepository.findByEmail(subject)
                .map(Usuario::getId)
                .orElseThrow(() -> new ValidacionExeption("Usuario no encontrado"));
    }
}
