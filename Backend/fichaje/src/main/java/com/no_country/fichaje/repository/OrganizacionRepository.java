package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacionRepository extends JpaRepository <Organizacion, Long> {

    Boolean existsByNumero(Integer numero);
}