package org.edutecno.backend.materia.controller;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.materia.dto.MateriaDTO;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {


    private final MateriaService materiaService;


    @GetMapping
    public ResponseEntity<ArrayList<MateriaModel>> listarMaterias() {
        return ResponseEntity.ok(materiaService.obtenerMaterias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        MateriaDTO materiaBuscada = materiaService.buscarMateriaPorId(id);
        return ResponseEntity.ok(materiaBuscada);
    }

    @PostMapping
    public ResponseEntity<MateriaModel> guardarMateria(@RequestBody MateriaDTO materia) {
        MateriaModel nuevaMateria = materiaService.guardarMateria(materia);
       return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaModel> editarMateria(@PathVariable Long id, @RequestBody MateriaDTO materia) {
        return ResponseEntity.ok(materiaService.actualizarMateria(id, materia));
    }

}
