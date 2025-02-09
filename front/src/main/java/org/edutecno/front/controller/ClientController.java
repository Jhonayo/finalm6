package org.edutecno.front.controller;

import org.edutecno.front.security.JwtTokenStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/home")
    public String showClientDashboard(Model model) {
        if (!"ROLE_CLIENT".equals(JwtTokenStorage.getRole())) {
            return "redirect:/login";
        }
        return "client/home";
    }
}
