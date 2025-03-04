package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.sesion.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SesionRepository extends JpaRepository <Sesion,Long> {

    Optional<Sesion> findByOrganizacionIdAndSesionKeyAndFinIsNull(Long organizacionId, String sesionKey);

}
