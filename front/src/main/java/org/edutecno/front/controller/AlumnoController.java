package org.edutecno.front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public String listarAlumnos(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        log.info("    --------  este es el metodo get de alumnos: " + token);
        if (token == null) {
            return "redirect:/login";
        }
       model.addAttribute("alumnos", alumnoService.obtenerAlumnos());
        return "index";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarAlumno(@PathVariable Long id, @RequestParam String nombreUsuario, @RequestParam List<Long> materias) {
        alumnoService.actualizarAlumno(id, nombreUsuario, materias);
        return "redirect:/admin/alumnos";
    }/*
    @PostMapping("/actualizar/{id}")
    public String actualizarAlumno(
        @PathVariable Long id,
        @RequestParam String nombreUsuario,
        @RequestParam List<Long> materias,
        HttpSession session
    ) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) {
            return "redirect:/login";
        }

        alumnoService.actualizarAlumno(id, nombreUsuario, materias);
        return "redirect:/admin/alumnos";
    }
    */
}