package com.no_country.fichaje.controller;

import com.no_country.fichaje.ValidacionExeption;
import com.no_country.fichaje.datos.dto.ActualizarUsuarioDTO;
import com.no_country.fichaje.datos.dto.RegistroUsuarioDTO;
import com.no_country.fichaje.datos.model.Usuario;
import com.no_country.fichaje.datos.model.Organizacion;
import com.no_country.fichaje.repository.UsuarioRepository;
import com.no_country.fichaje.service.OrganizacionService;
import com.no_country.fichaje.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.no_country.fichaje.infra.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid RegistroUsuarioDTO usuario){
        try{
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "usuario creado con éxito" + nuevoUsuario));
        }catch (ValidacionExeption e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @GetMapping()
    public ResponseEntity<?> verDatosUsuario(@RequestHeader ("Authorization") String token){
       try {
           Long usuarioId = tokenService.obtenerIdDesdeToken(token);
           List<Organizacion> organizaciones = organizacionService.listarOrganizacionesPorUsuario(usuarioId);
           return ResponseEntity.ok(organizaciones);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body(Map.of("error", "Token inválido o expirado"));
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id){
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ActualizarUsuarioDTO dto){
        try {
            Usuario usuario = usuarioService.actualizarUsuario(id, dto);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
