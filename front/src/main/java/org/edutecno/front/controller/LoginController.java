package org.edutecno.front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.security.JwtTokenStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {
    private final RestTemplate restTemplate;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        log.info("Iniciando proceso de login para email: {}", email);

        try {
            String url = "http://localhost:8080/auth/login";
            log.debug("URL de autenticación: {}", url);

            // Crear request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);
            log.debug("Request body creado: {}", requestBody);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
            log.debug("Headers configurados: {}", headers);

            // Hacer la petición
            log.info("Realizando petición HTTP al servicio de autenticación");
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            log.debug("Respuesta recibida. Status: {}", response.getStatusCode());
            log.debug("Cuerpo de la respuesta: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Autenticación exitosa");

                String accessToken = (String) response.getBody().get("access_token");
                String refreshToken = (String) response.getBody().get("refresh_token");


                log.info("Access Token obtenido: {}", accessToken != null ? "presente" : "null");
                log.info("Refresh Token obtenido: {}", refreshToken != null ? "presente" : "null");

                if (response.getBody() == null) {
                    log.error("El cuerpo de la respuesta es null");
                    model.addAttribute("error", "Respuesta inválida del servidor");
                    return "login";
                }

                String role = JwtTokenStorage.extractRoleFromToken(accessToken);
                log.debug("Rol extraído del token: {}", role);

                if (role == null) {
                    log.error("No se pudo extraer el rol del token.");
                    model.addAttribute("error", "Error al obtener el rol de usuario");
                    return "login";
                }

                // **Almacenar en la sesión**
                session.setAttribute("jwt", accessToken);
                session.setAttribute("role", role);
                log.info("Token y rol almacenados en la sesión correctamente.");
                log.info(" ----- el rol de la sesion es : {}", role);
                    log.info("    ******   reenviando a alumnos *****");
                return "redirect:/alumnos";
            } else {
                log.warn("Autenticación fallida. Status code: {}", response.getStatusCode());
                model.addAttribute("error", "Credenciales inválidas");
                return "login";
            }

        } catch (RestClientException e) {
            log.error("Error al realizar la petición HTTP", e);
            model.addAttribute("error", "Error al intentar iniciar sesión: " + e.getMessage());
            return "login";
        } catch (Exception e) {
            log.error("Error inesperado durante el login", e);
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            return "login";
        }
    }
}





