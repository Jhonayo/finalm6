package org.edutecno.front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.dto.AlumnoDTO;
import org.edutecno.front.service.AlumnoService;
import org.edutecno.front.service.MateriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
    private final AlumnoService alumnoService;
    private final MateriaService materiaService;

    public AlumnoController(AlumnoService alumnoService, MateriaService materiaService) {
        this.alumnoService = alumnoService;
        this.materiaService = materiaService;
    }

    @GetMapping
    public String listarAlumnos(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) {
            return "redirect:/login";
        }
       model.addAttribute("alumnos", alumnoService.obtenerAlumnos());
        return "index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        model.addAttribute("alumno", new AlumnoDTO());
        model.addAttribute("materias", materiaService.listarMaterias());
        return "admin/nuevo-alumno";
    }

    @PostMapping("/guardar")
    public String guardarAlumno(@ModelAttribute("alumno") AlumnoDTO alumnoDTO, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        alumnoService.guardarAlumno(alumnoDTO);
        return "redirect:/alumnos";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioAlumno(@PathVariable Long id, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        AlumnoDTO alumno = alumnoService.obtenerAlumnoPorId(id);
        model.addAttribute("alumno", alumno);
        model.addAttribute("materias", materiaService.listarMaterias());
        return "admin/actualizar-alumno";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarAlumno(@PathVariable Long id, @ModelAttribute AlumnoDTO alumnoDTO, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        alumnoService.actualizarAlumno(alumnoDTO);
        return "redirect:/alumnos";
    }

}