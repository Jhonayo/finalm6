package org.edutecno.front.controller;
import org.edutecno.front.service.MateriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMateria(@PathVariable Long id, @RequestParam String nombreMateria) {
        materiaService.actualizarMateria(id, nombreMateria);
        return "redirect:/admin/materias";
    }
}

