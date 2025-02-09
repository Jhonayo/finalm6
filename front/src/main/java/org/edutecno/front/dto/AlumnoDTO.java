package org.edutecno.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDTO {

    private String id;
    private String rut;
    private String nombre;
    private String direccion;
    private List<Long> materiasIds;

}
