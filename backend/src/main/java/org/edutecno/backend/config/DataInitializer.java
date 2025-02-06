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
        //Verificar si ya existen materias
        if (materiaRepository.count() > 0) {
            System.out.println("Los datos iniciales ya existen. No se insertarán duplicados.");
            return;
        }

        System.out.println("Insertando datos iniciales...");

        // Crear y guardar materias en la base de datos
        MateriaModel materia1 = materiaRepository.save(new MateriaModel("Lenguaje"));
        MateriaModel materia2 = materiaRepository.save(new MateriaModel("Matemática"));

        // Recuperar las materias guardadas
        materia1 = materiaRepository.findById(materia1.getId()).orElseThrow();
        materia2 = materiaRepository.findById(materia2.getId()).orElseThrow();

        //  Crear alumnos
        AlumnoModel alumno1 = new AlumnoModel("741", "Juan", "Belloto");
        AlumnoModel alumno2 = new AlumnoModel("852", "Fenrir", "Belloto");

        // Asignar materias a los alumnos
        alumno1.setMaterias(new HashSet<>(List.of(materia1, materia2)));
        alumno2.setMaterias(new HashSet<>(List.of(materia2)));

        // Guardar alumnos en la base de datos
        alumnoRepository.save(alumno1);
        alumnoRepository.save(alumno2);

        System.out.println("Datos iniciales cargados correctamente.");

        // 🔍 Verificar si ya existen usuarios
        if (userRepository.count() > 0) {
            System.out.println(" Los usuarios ya existen. No se insertarán duplicados.");
            return;
        }

        System.out.println("Creando usuarios iniciales...");

        //  Crear usuario ADMIN
        User adminUser = User.builder()
                .name("Admin")
                .email("admin@mail.com")
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ROLE_ADMIN)
                .build();

        // Crear usuario CLIENT
        User clientUser = User.builder()
                .name("User")
                .email("user@mail.com")
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .role(Role.ROLE_CLIENT)
                .build();

        // Guardar usuarios en la base de datos
        userRepository.save(adminUser);
        userRepository.save(clientUser);

        System.out.println("Usuarios iniciales creados correctamente.");
    }
}
