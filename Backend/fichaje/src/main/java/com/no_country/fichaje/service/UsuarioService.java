package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.dto.RegistroUsuarioDTO;
import com.no_country.fichaje.datos.model.Usuario;
import com.no_country.fichaje.infra.security.SecurityConfiguration;
import com.no_country.fichaje.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.no_country.fichaje.datos.dto.ActualizarUsuarioDTO;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearUsuario(@Valid RegistroUsuarioDTO usuario) {

        if(usuarioRepository.existsByEmail(usuario.email())){
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
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado con correo: " + email));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    public Usuario actualizarUsuario(Long id, ActualizarUsuarioDTO dto) {
        Usuario usuario = buscarPorId(id);

        if (dto.nombre() != null) {
            usuario.setNombre(dto.nombre());
            }

            if (dto.contrasena() != null && !dto.contrasena().isEmpty()) {
                usuario.setContrasena(passwordEncoder.encode(dto.contrasena()));
            }

            return usuarioRepository.save(usuario);
        }
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}


