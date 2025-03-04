package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColaboradorRepository extends JpaRepository<Colaboradores, Long> {
    boolean existsByDni(int dni);
    List<Colaboradores> findByOrganizacionId(Long organizacionId);
}
