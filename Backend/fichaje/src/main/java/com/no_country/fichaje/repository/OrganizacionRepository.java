package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.organizacion.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacionRepository extends JpaRepository <Organizacion, Long> {
    Organizacion findByNumeroAndPassword(Integer numero, String password);

    Boolean existsByNumero(int numero);
}