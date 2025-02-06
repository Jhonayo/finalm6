package org.edutecno.backend.materia.controller;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.service.MateriaService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {


    private final MateriaService materiaService;


    @GetMapping
    public ArrayList<MateriaModel> listarMaterias() {
        return materiaService.obtenerMaterias();
    }

    @PostMapping
    public MateriaModel guardarMateria(@RequestBody MateriaModel materia) {
        return this.materiaService.guardarMateria(materia);
    }
}
