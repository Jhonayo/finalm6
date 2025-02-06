package org.edutecno.backend.alumno.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.edutecno.backend.materia.model.MateriaModel;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alumnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Long id;

    private String rut;
    private String nombre;

    private String direccion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "alumno_materia",
            joinColumns = @JoinColumn(name = "id_alumno"),
            inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
    @JsonManagedReference
    private Set<MateriaModel> materias = new HashSet<>();

    public AlumnoModel(String rut, String nombre, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.direccion = direccion;
    }
}
