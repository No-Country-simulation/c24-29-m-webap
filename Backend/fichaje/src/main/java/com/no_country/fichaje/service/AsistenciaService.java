package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciasRepository asistenciasRepository;

    // Método que se invoca al realizar el escaneo facial
    public void registrarFichaje(Colaboradores colaborador) {
        Date now = new Date();
        // Buscar un registro abierto para el colaborador en el día actual
        Asistencias asistenciaAbierta = asistenciasRepository
                .findRegistroAbiertoByColaboradorAndFecha(colaborador, now);

        if (asistenciaAbierta != null) {
            // Se encontró un registro abierto: se asigna la hora de salida
            asistenciaAbierta.setSalida(now);
            // Aquí puedes calcular la diferencia de tiempo y realizar otras operaciones si es necesario
            asistenciasRepository.save(asistenciaAbierta);
        } else {
            // No existe registro abierto: se crea uno nuevo con la hora de entrada
            Asistencias nuevaAsistencia = new Asistencias();
            nuevaAsistencia.setColaborador(colaborador);
            nuevaAsistencia.setEntrada(now);
            nuevaAsistencia.setFechaRegistro(now); // Puedes ajustar este campo según la lógica de tu negocio
            nuevaAsistencia.setPresente(true); // O asignarlo según corresponda
            asistenciasRepository.save(nuevaAsistencia);
        }
    }
}
