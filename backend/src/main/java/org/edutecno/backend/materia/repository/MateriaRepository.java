package org.edutecno.backend.materia.repository;

import org.edutecno.backend.materia.model.MateriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaModel, Long> {
}
