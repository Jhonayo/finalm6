package org.edutecno.front.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.dto.AlumnoDTO;
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
        ResponseEntity<Map[]> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos",
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                Map[].class
        );
        return Arrays.asList(response.getBody());
    }


    public void guardarAlumno(AlumnoDTO alumnoDTO) {

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos",
                HttpMethod.POST,
                new HttpEntity<>(alumnoDTO, crearHeaders()),
                Void.class
        );

        log.info("Respuesta del servidor al guardar alumno: " + response.getStatusCode());
    }

    public AlumnoDTO obtenerAlumnoPorId(Long id) {
        ResponseEntity<AlumnoDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos/" + id,
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                AlumnoDTO.class
        );
        return response.getBody();
    }



    public void actualizarAlumno(AlumnoDTO alumnoDTO) {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos/" + alumnoDTO.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(alumnoDTO, crearHeaders()),
                Void.class
        );
    }

    private HttpHeaders crearHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String token = (String) session.getAttribute("jwt");
        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "Bearer " + token);
        } else {
            log.warn(" No se encontró un token en la sesión.");
        }
        return headers;
    }
}



















