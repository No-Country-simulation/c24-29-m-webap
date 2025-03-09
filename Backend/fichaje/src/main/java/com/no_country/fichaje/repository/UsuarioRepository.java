package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(@Email(message = "El correo electrónico no es válido") String email);

   Optional<Usuario> findByEmail(@Email(message = "El correo electrónico debe ser válido") @NotBlank(message = "El correo electrónico no puede estar vacío") String email);

}
