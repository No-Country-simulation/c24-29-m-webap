package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciasRepository asistenciasRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SesionRepository sesionRepository;

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

    public Asistencias registrarAsistencia(String sessionKey, Long colaboradorId, byte[] imagenCapturada) {
        // 1. Validar la sesión
        Sesion sesion = sesionRepository.findBySessionKey(sessionKey);
        if(sesion == null || new Date().after(sesion.getFin())) {
            throw new RuntimeException("Sesión inválida o expirada");
        }

        // 2. Validar el colaborador y su pertenencia a la organización
        Colaboradores colaborador = colaboradorRepository.findById(colaboradorId)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));
        if(!colaborador.getOrganizacion().getId().equals(sesion.getOrganizacion().getId())) {
            throw new RuntimeException("El colaborador no pertenece a esta organización");
        }

        // 3. Procesar el reconocimiento facial
        boolean reconocimientoOk = procesarReconocimientoFacial(colaborador.getFrente(), imagenCapturada);
        if(!reconocimientoOk) {
            throw new RuntimeException("No se pudo verificar la identidad del colaborador");
        }

        // 4. Registrar asistencia: si ya existe un registro sin salida, se marca salida; si no, se marca entrada.
        Asistencias asistencia = asistenciasRepository.findByColaboradorAndFechaRegistro(colaborador, new Date());
        if(asistencia == null) {
            // Registrar entrada
            asistencia = new Asistencias();
            asistencia.setColaborador(colaborador);
            asistencia.setOrganizacion(colaborador.getOrganizacion());
            asistencia.setSesion(sesion);
            asistencia.setFechaRegistro(new Date());
            asistencia.setEntrada(new Date());
        } else if(asistencia.getSalida() == null) {
            // Registrar salida
            asistencia.setSalida(new Date());
        } else {
            throw new RuntimeException("La jornada ya fue cerrada");
        }

        return asistenciasRepository.save(asistencia);
    }
    private boolean procesarReconocimientoFacial(byte[] imagenRegistrada, byte[] imagenCapturada) {
        // Aquí se integraría el algoritmo o servicio de reconocimiento facial
        // Retornar true si coincide, false en caso contrario.
        return true; // Simulación
    }

}

