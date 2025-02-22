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


    public void registrarFichaje(Colaboradores colaborador) {
        Date now = new Date();

        Asistencias asistenciaAbierta = asistenciasRepository
                .findRegistroAbiertoByColaboradorAndFecha(colaborador, now);

        if (asistenciaAbierta != null) {

            asistenciaAbierta.setSalida(now);

            asistenciasRepository.save(asistenciaAbierta);
        } else {

            Asistencias nuevaAsistencia = new Asistencias();
            nuevaAsistencia.setColaborador(colaborador);
            nuevaAsistencia.setEntrada(now);
            nuevaAsistencia.setFechaRegistro(now);
            nuevaAsistencia.setPresente(true);
            asistenciasRepository.save(nuevaAsistencia);
        }
    }

    public Asistencias registrarAsistencia(String sessionKey, Long colaboradorId, byte[] imagenCapturada) {

        Sesion sesion = sesionRepository.findBySessionKey(sessionKey);
        if(sesion == null || new Date().after(sesion.getFin())) {
            throw new RuntimeException("Sesión inválida o expirada");
        }


        Colaboradores colaborador = colaboradorRepository.findById(colaboradorId)
                .orElseThrow(() -> new RuntimeException("Colaborador no encontrado"));
        if(!colaborador.getOrganizacion().getId().equals(sesion.getOrganizacion().getId())) {
            throw new RuntimeException("El colaborador no pertenece a esta organización");
        }


        boolean reconocimientoOk = procesarReconocimientoFacial(colaborador.getFrente(), imagenCapturada);
        if(!reconocimientoOk) {
            throw new RuntimeException("No se pudo verificar la identidad del colaborador");
        }


        Asistencias asistencia = asistenciasRepository.findByColaboradorAndFechaRegistro(colaborador, new Date());
        if(asistencia == null) {

            asistencia = new Asistencias();
            asistencia.setColaborador(colaborador);
            asistencia.setOrganizacion(colaborador.getOrganizacion());
            asistencia.setSesion(sesion);
            asistencia.setFechaRegistro(new Date());
            asistencia.setEntrada(new Date());
        } else if(asistencia.getSalida() == null) {

            asistencia.setSalida(new Date());
        } else {
            throw new RuntimeException("La jornada ya fue cerrada");
        }

        return asistenciasRepository.save(asistencia);
    }
    private boolean procesarReconocimientoFacial(byte[] imagenRegistrada, byte[] imagenCapturada) {

        return true;

}

