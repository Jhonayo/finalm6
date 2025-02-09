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
public class MateriaService {
    private final RestTemplate restTemplate;
    private final HttpSession session;

    public MateriaService(RestTemplate restTemplate, HttpSession session1) {
        this.restTemplate = restTemplate;
        this.session = session1;
    }

    public List<Map<String,Object>> listarMaterias() {
        log.info(" --- Ingreso de materias servicio listar");
        ResponseEntity<Map[]> response = restTemplate.exchange(
                "http://localhost:8080/api/materias",
                HttpMethod.GET,
                new HttpEntity<>(null, crearHeaders()),
                Map[].class
        );
        return Arrays.asList(response.getBody());
    }


    public void actualizarMateria(Long id, String nombreMateria) {
        String url = "http://localhost:8080/api/materias/" + id;
        Map<String, String> request = Map.of("nombreMateria", nombreMateria);
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, crearHeaders()), Void.class);
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
