package org.edutecno.front.controller;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.edutecno.front.dto.MateriaDto;
import org.edutecno.front.service.MateriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping()
    public String materias(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        model.addAttribute("materias", materiaService.listarMaterias());
        model.addAttribute("materiaNueva", new MateriaDto());
        return "lista-materias";
    }

    @PostMapping("/guardar")
    public String guardarMateria(@ModelAttribute("materiaNueva")MateriaDto materiaDto, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        materiaService.guardarNuevaMateria(materiaDto);
        return "redirect:/materias";

    }
    @GetMapping("/actualizar/{id}")
    public String actualizarMateria(@PathVariable int id, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        MateriaDto materia = materiaService.obtenerMateriaPorId(id);
        model.addAttribute("materia", materia);
        return "admin/actualizar-materia";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMateria(@PathVariable Long id, @ModelAttribute MateriaDto materiaDto, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("jwt");
        if (token == null || !"ROLE_ADMIN".equals(role)) {
            return "redirect:/alumnos";
        }
        materiaService.actualizarMateria(materiaDto);
        return "redirect:/materias";
    }
}

