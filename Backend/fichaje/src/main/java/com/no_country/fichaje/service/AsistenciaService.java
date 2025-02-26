package com.no_country.fichaje.service;


import com.no_country.fichaje.datos.colaboradores.Colaboradores;
import com.no_country.fichaje.datos.sesion.Sesion;
import com.no_country.fichaje.repository.AsistenciasRepository;
import com.no_country.fichaje.repository.ColaboradorRepository;
import com.no_country.fichaje.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.*;
import java.util.stream.Collectors;

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

    public Sesion inicioYFinSesion(byte[] imagenCapturada) {
        return null;
    }

    public Mono<Map<String,?>> reconocimiento(byte[] imagenCapturada) {

        String captura = CompreFaceService.encodeToBase64(imagenCapturada);
        String apiKey = "4a01ad25-1662-4917-b6b4-b6ee2bfd98b2";

        List<Colaboradores> colaboradores = colaboradorRepository.findAll();
        List<String> imagenes = colaboradores.stream()
                .map(colaborador -> {
                    if (colaborador.getFrente() != null) {
                        return Base64.getEncoder().encodeToString(colaborador.getFrente());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (imagenes.isEmpty()) {
            return Mono.just(Map.of("mensaje", "No hay imágenes de colaboradores para comparar"));
        }
        return compreFaceService.detectFace(apiKey, captura, imagenes)
                .flatMap(response -> {
                    if (response != null && response.containsKey("result")) {
                        List<Map<String, Object>> resultados = (List<Map<String, Object>>) response.get("result");
                        Optional<Map<String, Object>> mejorCoincidencia = resultados.stream()
                                .filter(r -> r.containsKey("similarity") && (double) r.get("similarity") >= 0.85)
                                .max(Comparator.comparing(r -> (double) r.get("similarity")));
                        if (mejorCoincidencia.isPresent()) {
                            Map<String, Object> coincidencia = mejorCoincidencia.get();
                            int index = resultados.indexOf(coincidencia);

                            if (index >= 0 && index < colaboradores.size()) {
                                Colaboradores colaboradorCoincidente = colaboradores.get(index);

                                Map<String, Object> result = new HashMap<>();
                                result.put("idColaborador", colaboradorCoincidente.getId());
                                result.put("idOrganizacion", colaboradorCoincidente.getOrganizacion().getId());
                                result.put("similarity", coincidencia.get("similarity"));
                                return Mono.just(result);
                            }
                        }
                    }
                    return Mono.just(Map.of("mensaje", "No hubo coincidencia"));
                })
                .onErrorResume(e -> {
                    return Mono.just(Map.of("mensaje", "Error al prosesar el reconocimiento facial: " + e.getMessage()));
                });
    }

}







