package org.edutecno.front.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.security.JwtTokenStorage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AlumnoService {
    private final RestTemplate restTemplate;
    private final HttpSession session;

    public AlumnoService(RestTemplate restTemplate, HttpSession session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }

    public List<Map<String, Object>> obtenerAlumnos() {
        log.info("  ---  ya entre en el alumno service obtener alumno ");
        ResponseEntity<Map[]> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos",
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                Map[].class
        );
        return Arrays.asList(response.getBody());
    }

    public void actualizarAlumno(Long id, String nombreUsuario, List<Long> materias) {
        Map<String, Object> requestBody = Map.of(
                "nombreUsuario", nombreUsuario,
                "materiasIds", materias
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, crearHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos/" + id,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        log.info("Respuesta del servidor: " + response.getStatusCode());
    }

    private HttpHeaders crearHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String token = (String) session.getAttribute("jwt");
        log.info("  ---  Método para crear el header  ---");
        log.info("   ******** Token del usuario: **********\n{}", token);

        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "Bearer " + token);
            log.debug(" -----   Enviando token en la petición: Bearer {}", token);
        } else {
            log.warn(" No se encontró un token en la sesión.");
        }

        log.info("----   Headers enviados en la petición: {}", headers);
        return headers;
    }

}



















