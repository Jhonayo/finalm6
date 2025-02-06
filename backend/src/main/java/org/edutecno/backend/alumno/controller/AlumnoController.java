package org.edutecno.backend.alumno.controller;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.alumno.dto.AlumnoDTO;
import org.edutecno.backend.alumno.model.AlumnoModel;
import org.edutecno.backend.alumno.service.AlumnoService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<AlumnoModel>> listar() {
        List<AlumnoModel> alumnos = alumnoService.listarAlumnos();
        return ResponseEntity.ok(alumnos);
    }

    @PostMapping()
    public ResponseEntity<AlumnoDTO> nuevoAlumno(@RequestBody AlumnoDTO alumnoDto){
        AlumnoDTO alumnoNuevo = alumnoService.guardarAlumno(alumnoDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(alumnoNuevo);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<AlumnoModel> obtenerAlumno(@PathVariable Long id) {
        AlumnoModel alumno = alumnoService.buscarAlumnoPorId(id);
        return ResponseEntity.ok(alumno);
    }

    @PostMapping
    public ResponseEntity<AlumnoModel> crearAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        AlumnoModel alumnoNuevo = alumnoService.crearAlumno(alumnoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoNuevo);
    }

    @PostMapping("/{id}")
    public ResponseEntity<AlumnoModel> actualizarAlumno(@PathVariable Long id, @RequestBody AlumnoDTO alumnoDTO) {
        AlumnoModel alumnoAtualizado = alumnoService.actualizarAlumno(id, alumnoDTO);
        return ResponseEntity.ok(alumnoAtualizado);
    }

    //no implementado

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.noContent().build();
    }
    */



}
