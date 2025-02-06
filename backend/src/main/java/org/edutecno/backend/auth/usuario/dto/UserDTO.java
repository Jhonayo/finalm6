package org.edutecno.backend.auth.usuario.dto;

import lombok.Data;
import org.edutecno.backend.auth.usuario.model.Role;

@Data
public class UserDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;

}
