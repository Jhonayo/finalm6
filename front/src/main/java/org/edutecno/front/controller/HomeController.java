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
        /*if (session.getAttribute("usuario") != null) {
            return "redirect:/index";
        }*/
        return "login";
    }
}
