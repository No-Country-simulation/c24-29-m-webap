package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ColaboradorRepository extends JpaRepository<Colaboradores, Long> {
    boolean existsByDni(int dni);
    List<Colaboradores> findByOrganizacionId(Long organizacionId);
}
