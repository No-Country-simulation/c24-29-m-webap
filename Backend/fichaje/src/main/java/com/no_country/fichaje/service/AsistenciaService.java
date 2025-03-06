package com.no_country.fichaje.service;

import com.no_country.fichaje.datos.asistencia.Asistencias;
import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
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
    private CompreFaceService compreFaceService;

    @Value("${api.key}")
    private String apiKey;

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

    public boolean tieneAsistenciaActiva(Colaboradores colaborador) {
        return asistenciasRepository.findRegistroAbiertoByColaborador(colaborador).isPresent();
    }

    @Transactional
    public void registrarEntrada(Colaboradores colaborador) {
        Date fechaRegistro = new Date();
        List<Asistencias> registrosHoy = asistenciasRepository.findByColaboradorAndFecha(colaborador, fechaRegistro);

        Asistencias nuevaAsistencia = new Asistencias();
        nuevaAsistencia.setColaborador(colaborador);
        nuevaAsistencia.setOrganizacion(colaborador.getOrganizacion());
        nuevaAsistencia.setFechaRegistro(fechaRegistro);
        nuevaAsistencia.setEntrada(fechaRegistro);

        if(registrosHoy.isEmpty()){
            nuevaAsistencia.setPresente(true);
            nuevaAsistencia.setEsExtra(false);
        } else{
            nuevaAsistencia.setPresente(false);
            nuevaAsistencia.setEsExtra(true);
        }
        asistenciasRepository.save(nuevaAsistencia);
    }

    @Transactional
    public void registrarSalida(Colaboradores colaborador) {
        Date fechaSalida = new Date();
        Asistencias asistencia = asistenciasRepository.findRegistroAbiertoByColaborador(colaborador)
                .orElseThrow(() -> new RuntimeException("No se encontró asistencia activa para el colaborador"));
        asistencia.setSalida(fechaSalida);
        asistencia.setPresente(false);
        asistenciasRepository.save(asistencia);
    }
}










