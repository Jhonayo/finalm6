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
        log.info("  ---  ya entre en el alumno service obtener alumno ");
        ResponseEntity<Map[]> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos",
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                Map[].class
        );
        return Arrays.asList(response.getBody());
    }


    public void guardarAlumno(AlumnoDTO alumnoDTO) {
        log.info("  ---  ya entre en el alumno service guardar alumno");

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos",
                HttpMethod.POST,
                new HttpEntity<>(alumnoDTO, crearHeaders()),
                Void.class // ✅ No esperamos una respuesta de tipo Map[]
        );

        log.info("Respuesta del servidor al guardar alumno: " + response.getStatusCode());
    }

    public AlumnoDTO obtenerAlumnoPorId(Long id) {
        log.info("  ---  Obteniendo alumno con ID: " + id);
        ResponseEntity<AlumnoDTO> response = restTemplate.exchange(
                "http://localhost:8080/api/alumnos/" + id,
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                AlumnoDTO.class
        );
        return response.getBody();
    }



    public void actualizarAlumno(Long id, String nombreUsuario, List<Long> materias) {
        Map<String, Object> requestBody = Map.of(
                "id", id,
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
        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "Bearer " + token);
        } else {
            log.warn(" No se encontró un token en la sesión.");
        }
        return headers;
    }
}



















