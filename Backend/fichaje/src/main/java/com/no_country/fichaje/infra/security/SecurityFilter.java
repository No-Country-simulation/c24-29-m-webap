package com.no_country.fichaje.infra.security;

import com.no_country.fichaje.datos.model.usuario.Usuario;
import com.no_country.fichaje.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.no_country.fichaje.ValidacionExeption;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenServis;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenServis, UsuarioRepository usuarioRepository) {
        this.tokenServis = tokenServis;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        if (token != null) {
            try {
                String subject = tokenServis.getSubject(token);
                if (subject != null) {
                    Usuario usuario = usuarioRepository.findByEmail(subject)
                            .orElseThrow(() -> new ValidacionExeption("usuario no encontrado"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );

                    // Agregar detalles de la autenticación
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                logger.error("Error al procesar el token JWT", e);
            }
        }

        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").substring(7);
        }
        return null;
    }

}
