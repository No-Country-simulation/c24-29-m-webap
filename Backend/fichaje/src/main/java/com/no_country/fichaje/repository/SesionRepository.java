package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.sesion.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository <Sesion,Long> {
    Sesion findBySessionKey(String sessionKey);
}
