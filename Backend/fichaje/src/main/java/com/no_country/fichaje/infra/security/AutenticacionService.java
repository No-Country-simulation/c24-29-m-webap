package com.no_country.fichaje.infra.security;

import com.no_country.fichaje.datos.model.Usuario;
import com.no_country.fichaje.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

        private final UsuarioRepository usuarioRepository;
        private final PasswordEncoder passwordEncoder;

        public AutenticacionService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
            this.usuarioRepository = usuarioRepository;
            this.passwordEncoder = passwordEncoder;
        }


    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getContrasena())
                .roles("USER")
                .build();
    }
        public UserDetails autenticar(String correoElectronico, String contrasena) {
               Usuario usuario = usuarioRepository.findByEmail(correoElectronico)
                    .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

            if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                throw new BadCredentialsException("Contraseña incorrecta");
            }

            return User.withUsername(usuario.getEmail())
                    .password(usuario.getContrasena())
                    .roles("USER")
                    .build();
        }

    }

