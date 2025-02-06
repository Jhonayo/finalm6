package org.edutecno.backend.alumno.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private List<Long> materiasIds;
}
