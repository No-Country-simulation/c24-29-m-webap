package com.no_country.fichaje.repository;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sesion.Sesion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.Optional;

public interface AsistenciasRepository extends JpaRepository<Asistencias, Long> {
    @Query("SELECT a FROM Asistencias a WHERE a.colaborador = :colaborador " +
            "AND FUNCTION('DATE', a.fechaRegistro) = FUNCTION('DATE', :fecha) " +
            "AND a.salida IS NULL")
    Asistencias findRegistroAbiertoByColaboradorAndFecha(@Param("colaborador") Colaboradores colaborador,
                                                         @Param("fecha") Date fecha);

    Optional<Asistencias> findByColaboradorAndSesion(Colaboradores colaborador, Sesion sesion);

    @Modifying
    @Transactional
    @Query("INSERT INTO Asistencias (colaborador, fechaRegistro, entrada, sesion) " +
            "VALUES (:colaborador, :fechaRegistro, :entrada, :sesion)")
    void registrarEntrada(@Param("colaborador") Colaboradores colaborador,
                          @Param("fechaRegistro") Date fechaRegistro,
                          @Param("entrada") Date entrada,
                          @Param("sesion") Sesion sesion);

    @Modifying
    @Transactional
    @Query("UPDATE Asistencias a SET a.salida = :salida WHERE a.id = :id")
    void registrarSalida(@Param("id") Long id, @Param("salida") Date salida);

    @Query("SELECT a FROM Asistencias a WHERE a.colaborador = :colaborador AND a.sesion = :sesion AND a.salida IS NULL")
    Optional<Asistencias> findRegistroAbiertoByColaboradorAndSesion(@Param("colaborador") Colaboradores colaborador,
                                                                    @Param("sesion") Sesion sesion);
}
