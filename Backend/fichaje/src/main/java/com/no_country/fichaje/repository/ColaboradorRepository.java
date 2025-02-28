package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaboradores, Long> {
    boolean existsByDni(int dni);
}
