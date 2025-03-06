package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.Usuario.RegistroUsuarioDTO;
import com.no_country.fichaje.datos.Usuario.Usuario;
import com.no_country.fichaje.infra.security.SecurityConfiguration;
import com.no_country.fichaje.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    private PasswordEncoder passwordEncoder;


    public Usuario crearUsuario(@Valid RegistroUsuarioDTO usuario) {

        if(usuarioRepository.existsByCorreoElectronico(usuario.email())){
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(usuario.nombre());
        nuevoUsuario.setEmail(usuario.email());
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuario.contrasena()));
        return usuarioRepository.save(nuevoUsuario);
    }
    public Usuario buscarPorEmail(
            @Email(message = "El correo electrónico debe ser válido")
            @NotBlank(message = "El correo electrónico no puede estar vacío") String email) {
        return usuarioRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));
    }
}

