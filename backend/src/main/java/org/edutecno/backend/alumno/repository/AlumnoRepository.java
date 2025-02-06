package org.edutecno.backend.alumno.repository;

import org.edutecno.backend.alumno.model.AlumnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoModel, Long> {

/*    @Query("SELECT a FROM AlumnoModel a LEFT JOIN FETCH a.materialist")
    List<AlumnoModel> findAllWithMaterias();*/

}
