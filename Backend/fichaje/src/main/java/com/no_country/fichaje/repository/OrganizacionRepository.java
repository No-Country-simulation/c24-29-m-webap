package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.model.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizacionRepository extends JpaRepository <Organizacion, Long> {

    Boolean existsByNumeroRegistro(Integer numeroRegistro);
    Optional<Organizacion> findByNumeroRegistro(Integer numeroRegistrado);
    List<Organizacion> findByUsuarioId(Long usuarioId);
}