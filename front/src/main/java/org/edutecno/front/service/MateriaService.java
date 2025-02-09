package org.edutecno.front.service;

import org.edutecno.front.security.JwtTokenStorage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class MateriaService {
    private final RestTemplate restTemplate;

    public MateriaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void actualizarMateria(Long id, String nombreMateria) {
        String url = "http://localhost:8080/api/materias/" + id;
        Map<String, String> request = Map.of("nombreMateria", nombreMateria);
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, crearHeaders()), Void.class);
    }

    private HttpHeaders crearHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JwtTokenStorage.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
