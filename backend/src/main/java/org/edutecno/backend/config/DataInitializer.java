package org.edutecno.backend.config;

import jakarta.transaction.Transactional;
import org.edutecno.backend.alumno.model.AlumnoModel;
import org.edutecno.backend.alumno.repository.AlumnoRepository;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.repository.MateriaRepository;
import org.edutecno.backend.usuario.model.Role;
import org.edutecno.backend.usuario.model.User;
import org.edutecno.backend.usuario.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MateriaRepository materiaRepository;
    private final AlumnoRepository alumnoRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(MateriaRepository materiaRepository, AlumnoRepository alumnoRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.materiaRepository = materiaRepository;
        this.alumnoRepository = alumnoRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (materiaRepository.count() > 0) {
            System.out.println("    Los datos iniciales ya existen. No se insertarán duplicados.");
            return;
        }

        System.out.println("    Insertando datos iniciales...");

        MateriaModel materia1 = materiaRepository.save(new MateriaModel("Lenguaje"));
        MateriaModel materia2 = materiaRepository.save(new MateriaModel("Matematica"));

        materia1 = materiaRepository.findById(materia1.getId()).orElseThrow();
        materia2 = materiaRepository.findById(materia2.getId()).orElseThrow();

        AlumnoModel alumno1 = new AlumnoModel("123456", "Juan Ramirez", "Belloto");
        AlumnoModel alumno2 = new AlumnoModel("789456", "Fenrir", "Belloto");

        alumno1.setMaterias(new HashSet<>(List.of(materia1, materia2)));
        alumno2.setMaterias(new HashSet<>(List.of(materia2)));

        alumnoRepository.save(alumno1);
        alumnoRepository.save(alumno2);

        System.out.println("    Datos iniciales cargados correctamente.");

        if (userRepository.count() > 0) {
            System.out.println("    Los usuarios ya existen. No se insertarán duplicados.");
            return;
        }

        System.out.println("    Creando usuarios iniciales...");
        User adminUser = User.builder()
                .name("Admin")
                .email("admin@mail.com")
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .build();

        User clientUser = User.builder()
                .name("User")
                .email("user@mail.com")
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(Role.ROLE_CLIENT)
                .build();

        userRepository.save(adminUser);
        userRepository.save(clientUser);

        System.out.println("    Usuarios iniciales creados correctamente.");
    }
}
