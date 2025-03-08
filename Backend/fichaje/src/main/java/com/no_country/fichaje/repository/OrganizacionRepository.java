package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.model.organizacion.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizacionRepository extends JpaRepository <Organizacion, Long> {

    Boolean existsByNumero(Integer numero);

    List<Organizacion> findByUsuarioId(Long usuarioId);
}