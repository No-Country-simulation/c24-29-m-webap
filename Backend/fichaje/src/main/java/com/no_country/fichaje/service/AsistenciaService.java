package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.SesionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciasRepository asistenciasRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private CompreFaceService compreFaceService;

    @Value("${api.key}")
    private String apiKey;

    public Optional<Sesion> validarSesionOrganizacion(Long organizacionId, String sesionKey) {
        return sesionRepository.findByOrganizacionIdAndSesionKeyAndFinIsNull(organizacionId, sesionKey);
    }

    public Optional<Colaboradores> reconocerColaborador(String imagenCapturada, Long organizacionId) {

        List<Colaboradores> colaboradores = colaboradorRepository.findByOrganizacionId(organizacionId);
        List<String> imagenes = colaboradores.stream()
                .map(Colaboradores::getFrente)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (imagenes.isEmpty()) {
            return Optional.empty();
        }

        Colaboradores colaborador = compreFaceService.detectFace("api-key", imagenCapturada, imagenes)
                .map(response -> {
                    if (response.containsKey("result")) {
                        List<Map<String, Object>> resultados = (List<Map<String, Object>>) response.get("result");
                        Optional<Map<String, Object>> mejorCoincidencia = resultados.stream()
                                .filter(r -> r.containsKey("similarity") && (double) r.get("similarity") >= 0.85)
                                .max(Comparator.comparing(r -> (double) r.get("similarity")));
                        if (mejorCoincidencia.isPresent()) {
                            int index = resultados.indexOf(mejorCoincidencia.get());
                            if (index >= 0 && index < colaboradores.size()) {
                                return colaboradores.get(index);
                            }
                        }
                    }
                    return null;
                })
                .block();

        return Optional.ofNullable(colaborador);
    }

    public boolean tieneAsistenciaActiva(Colaboradores colaborador, Sesion sesion) {
        return asistenciasRepository.findRegistroAbiertoByColaboradorAndSesion(colaborador, sesion).isPresent();
    }

    @Transactional
    public void registrarEntrada(Colaboradores colaborador, Sesion sesion) {
        Date fechaRegistro = new Date();
        Asistencias nuevaAsistencia = new Asistencias();
        nuevaAsistencia.setColaborador(colaborador);
        nuevaAsistencia.setOrganizacion(colaborador.getOrganizacion());
        nuevaAsistencia.setSesion(sesion);
        nuevaAsistencia.setFechaRegistro(fechaRegistro);
        nuevaAsistencia.setEntrada(fechaRegistro);
        asistenciasRepository.save(nuevaAsistencia);
    }

    @Transactional
    public void registrarSalida(Colaboradores colaborador, Sesion sesion) {
        Date fechaSalida = new Date();
        Asistencias asistencia = asistenciasRepository.findRegistroAbiertoByColaboradorAndSesion(colaborador, sesion)
                .orElseThrow(() -> new RuntimeException("No se encontró asistencia activa para el colaborador"));
        asistencia.setSalida(fechaSalida);
        asistenciasRepository.save(asistencia);
    }
}









