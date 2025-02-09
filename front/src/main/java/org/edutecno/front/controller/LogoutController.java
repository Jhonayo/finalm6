package org.edutecno.front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    @GetMapping
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
