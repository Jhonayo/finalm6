package org.edutecno.backend.alumno.controller;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.alumno.dto.AlumnoDTO;
import org.edutecno.backend.alumno.model.AlumnoModel;
import org.edutecno.backend.alumno.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<ArrayList<AlumnoModel>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.listarAlumnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> obtenerAlumno(@PathVariable Long id) {
        AlumnoDTO alumnoBuscado = alumnoService.buscarAlumnoPorId(id);
        return ResponseEntity.ok(alumnoBuscado);
    }

    @PostMapping()
    public ResponseEntity<AlumnoDTO> nuevoAlumno(@RequestBody AlumnoDTO alumnoDto){
        AlumnoDTO alumnoNuevo = alumnoService.guardarAlumno(alumnoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> actualizarAlumno(@PathVariable Long id, @RequestBody AlumnoDTO alumnoDTO) {
       AlumnoDTO alumnoActualizado = alumnoService.actualizarAlumno(id, alumnoDTO);
       return ResponseEntity.ok(alumnoActualizado);
    }



}
