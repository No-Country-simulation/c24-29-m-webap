package com.no_country.fichaje.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@Service
public class CompreFaceService {

private final WebClient webClient;

    public CompreFaceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000/api/v1").build();
    }
    public Mono<Map<String, Object>> detectFace(String apiKey, String imageBase64, List<String> imagenesGuardadasBase64) {
        return webClient.post()
                .uri("/verify")
                .header("x-api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(Map.of(
                        "image", imageBase64,
                        "images", imagenesGuardadasBase64))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> Mono.just(Map.of("error", "Error en la llamada a CompreFace: " + e.getMessage())));
    }

}
