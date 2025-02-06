package org.edutecno.backend.config;

import jakarta.transaction.Transactional;
import org.edutecno.backend.alumno.model.AlumnoModel;
import org.edutecno.backend.alumno.repository.AlumnoRepository;
import org.edutecno.backend.materia.model.MateriaModel;
import org.edutecno.backend.materia.repository.MateriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MateriaRepository materiaRepository;
    private final AlumnoRepository alumnoRepository;

    public DataInitializer(MateriaRepository materiaRepository, AlumnoRepository alumnoRepository) {
        this.materiaRepository = materiaRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 🔍 Verificar si ya existen materias
        if (materiaRepository.count() > 0) {
            System.out.println("ℹ️ Los datos iniciales ya existen. No se insertarán duplicados.");
            return;
        }

        System.out.println("🔰 Insertando datos iniciales...");

        // 1️⃣ Crear y guardar materias en la base de datos
        MateriaModel materia1 = materiaRepository.save(new MateriaModel("Lenguaje"));
        MateriaModel materia2 = materiaRepository.save(new MateriaModel("Matemática"));

        // 2️⃣ Recuperar las materias guardadas
        materia1 = materiaRepository.findById(materia1.getId()).orElseThrow();
        materia2 = materiaRepository.findById(materia2.getId()).orElseThrow();

        // 3️⃣ Crear alumnos
        AlumnoModel alumno1 = new AlumnoModel("741", "Juan", "Belloto");
        AlumnoModel alumno2 = new AlumnoModel("852", "Fenrir", "Belloto");

        // 4️⃣ Asignar materias a los alumnos
        alumno1.setMaterias(new HashSet<>(List.of(materia1, materia2)));
        alumno2.setMaterias(new HashSet<>(List.of(materia2)));

        // 5️⃣ Guardar alumnos en la base de datos
        alumnoRepository.save(alumno1);
        alumnoRepository.save(alumno2);

        System.out.println("✅ Datos iniciales cargados correctamente.");
    }
}
