package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AsistenciasRepository extends JpaRepository<Asistencias, Long> {
    @Query("SELECT a FROM Asistencias a WHERE a.colaborador = :colaborador " +
            "AND FUNCTION('DATE', a.fechaRegistro) = FUNCTION('DATE', :fecha) " +
            "AND a.salida IS NULL")
    Asistencias findRegistroAbiertoByColaboradorAndFecha(@Param("colaborador") Colaboradores colaborador,
                                                         @Param("fecha") Date fecha);

    Asistencias findByColaboradorAndFechaRegistro(Colaboradores colaborador, Date date);
}
