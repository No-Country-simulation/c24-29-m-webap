package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.model.Asistencias;
import com.no_country.fichaje.datos.model.Colaboradores;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface AsistenciasRepository extends JpaRepository<Asistencias, Long> {
    @Query("SELECT a FROM Asistencias a WHERE a.colaborador = :colaborador " +
            "AND FUNCTION('DATE', a.fechaRegistro) = FUNCTION('DATE', :fecha) " +
            "AND a.salida IS NULL")
    List<Asistencias> findRegistroAbiertoByColaboradorAndFecha(@Param("colaborador") Colaboradores colaborador,
                                                         @Param("fecha") Date fecha);

    @Modifying
    @Transactional
    @Query("UPDATE Asistencias a SET a.salida = :salida WHERE a.id = :id")
    void registrarSalida(@Param("id") Long id, @Param("salida") Date salida);


    Optional<Asistencias> findByColaboradorAndSalidaIsNull(Colaboradores colaborador);


    List<Asistencias> findByColaborador(Colaboradores colaborador);

    List<Asistencias> findByColaboradorAndFechaRegistroBetween(Colaboradores colaborador, Date fechaInicio, Date fechaFin);
    List<Asistencias> findByOrganizacionIdAndFechaRegistroBetween(Long organizacionId, Date fechaInicio, Date fechaFin);
}