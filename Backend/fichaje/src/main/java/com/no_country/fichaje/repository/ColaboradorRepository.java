package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.model.Colaboradores;
import com.no_country.fichaje.datos.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaboradores, Long> {
    boolean existsByDni(int dni);
    List<Colaboradores> findByOrganizacionId(Long organizacionId);
    Optional<Colaboradores> findByFrenteAndOrganizacionId(String frente, Long organizacionId);
    List<Colaboradores> findByEstadoAndOrganizacionId(Estado estado, Long organizacionId);
}

