package org.edutecno.front.controller;
import org.edutecno.front.security.JwtTokenStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Controller
public class DashboardController {
    private final RestTemplate restTemplate;

    public DashboardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        String url = "http://localhost:8080/api/alumnos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JwtTokenStorage.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        model.addAttribute("alumnos", response.getBody());
        return "dashboard";
    }
}
