package org.edutecno.backend.materia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.edutecno.backend.alumno.model.AlumnoModel;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "materias")
@Getter
@Setter
@NoArgsConstructor
public class MateriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id;

    @Column(name = "nombre_materia")
    private String nombre;

    @ManyToMany(mappedBy = "materias")
    @JsonBackReference
    private Set<AlumnoModel> alumnoModel = new HashSet<>();

    public MateriaModel(String nombre) {
        this.nombre = nombre;
    }

}
