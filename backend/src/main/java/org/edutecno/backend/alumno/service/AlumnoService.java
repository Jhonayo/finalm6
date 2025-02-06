package org.edutecno.backend.alumno.service;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.alumno.dto.AlumnoDTO;
import org.edutecno.backend.alumno.model.AlumnoModel;
import org.edutecno.backend.alumno.repository.AlumnoRepository;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    @Transactional(readOnly = true)
    public List<AlumnoModel> listarAlumnos() {
        return alumnoRepository.findAll();
    }


    public ArrayList<AlumnoModel> obtenerAlumnos() {
        return (ArrayList<AlumnoModel>) alumnoRepository.findAll();
    }

    public AlumnoDTO guardarAlumno(AlumnoDTO alumnoDto) {
        AlumnoModel alumnoModel = new AlumnoModel();
        alumnoModel.setRut(alumnoDto.getRut());
        alumnoModel.setNombre(alumnoDto.getNombre());
        alumnoModel.setDireccion(alumnoDto.getDireccion());

        List<MateriaModel> materias = materiaRepository.findAllById(alumnoDto.getMateriasIds());
        alumnoModel.setMaterias(new HashSet<>(materias));
        AlumnoModel alumnoCreado = alumnoRepository.save(alumnoModel);

        return alumnoDtoParaRespuesta(alumnoCreado);
    }
    private AlumnoDTO alumnoDtoParaRespuesta(AlumnoModel alumnoModel) {
        AlumnoDTO alumnoDto = new AlumnoDTO();
        alumnoDto.setRut(alumnoModel.getRut());
        alumnoDto.setNombre(alumnoModel.getNombre());
        alumnoDto.setDireccion(alumnoModel.getDireccion());
        alumnoDto.setMateriasIds(alumnoModel.getMaterias().stream().map(MateriaModel::getId).collect(Collectors.toList()));
        return alumnoDto;
    }


}
