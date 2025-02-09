package org.edutecno.front.controller;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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
        log.info("\n  ----verificando entrada");
        log.debug("\n  ----verificando entrada");
        String token = (String) session.getAttribute("jwt");
        log.info("    --------  este es el metodo get de materias: " + token);
        if (token == null) {
            log.info("--- Me enviara al Login");
            log.debug("--- Me enviara al Login por debug");
            return "redirect:/alumnos";
        }
        model.addAttribute("materias", materiaService.listarMaterias());
        return "lista-materias";

    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMateria(@PathVariable Long id, @RequestParam String nombreMateria) {
        materiaService.actualizarMateria(id, nombreMateria);
        return "redirect:/admin/materias";
    }
}

