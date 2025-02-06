package org.edutecno.backend.materia.service;

import lombok.RequiredArgsConstructor;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public ArrayList<MateriaModel> obtenerMaterias() {
        return (ArrayList<MateriaModel>) materiaRepository.findAll();
    }

    public MateriaModel guardarMateria(MateriaModel materia) {
        return materiaRepository.save(materia);
    }

}
