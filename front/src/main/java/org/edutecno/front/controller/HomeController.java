package org.edutecno.front.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(HttpSession session) {
        if (session.getAttribute("role") != "ROLE_ADMIN") {
            return "redirect:/alumnos";
        }
        return "login";
    }
}
