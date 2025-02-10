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
        try {
            String url = "http://localhost:8080/auth/login";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Autenticación exitosa");

                String accessToken = (String) response.getBody().get("access_token");
                String refreshToken = (String) response.getBody().get("refresh_token");


                if (response.getBody() == null) {
                    model.addAttribute("error", "Respuesta inválida del servidor");
                    return "login";
                }

                String role = JwtTokenStorage.extractRoleFromToken(accessToken);
                log.debug("Rol extraído del token: {}", role);

                if (role == null) {
                    model.addAttribute("error", "Error al obtener el rol de usuario");
                    return "login";
                }

                session.setAttribute("jwt", accessToken);
                session.setAttribute("role", role);

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





