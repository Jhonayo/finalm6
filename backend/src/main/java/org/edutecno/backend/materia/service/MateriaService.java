package org.edutecno.backend.materia.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.edutecno.backend.materia.dto.MateriaDTO;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;

    @Transactional(readOnly = true)
    public ArrayList<MateriaModel> obtenerMaterias() {
        return (ArrayList<MateriaModel>) materiaRepository.findAll();
    }

    public MateriaModel guardarMateria(@RequestBody MateriaDTO materia) {
        if(materia.getNombre() == null || materia.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del materia no puede ser vacio");
        }
        MateriaModel materiaModel = new MateriaModel();
        materiaModel.setNombre(materia.getNombre());
        return materiaRepository.save(materiaModel);
    }

    public MateriaModel actualizarMateria(Long id, MateriaDTO materia) {
      return materiaRepository.findById(id)
              .map(materiaExistente ->{
                  materiaExistente.setNombre(materia.getNombre());
                  return materiaRepository.save(materiaExistente);
              })
              .orElseThrow(() -> new EntityNotFoundException("Materia no encontrada"));
    }
}
