package com.kosmos.hospitalmanagement.config;

import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.repository.ConsultorioRepository;
import com.kosmos.hospitalmanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Cargar datos iniciales de doctores
        doctorRepository.save(new Doctor("John", "Doe", "Smith", "Cardiología"));
        doctorRepository.save(new Doctor("Jane", "Doe", "Johnson", "Neurología"));
        doctorRepository.save(new Doctor("James", "Brown", "Williams", "Pediatría"));

        // Cargar datos iniciales de consultorios
        consultorioRepository.save(new Consultorio(101, "1º Piso"));
        consultorioRepository.save(new Consultorio(102, "1º Piso"));
        consultorioRepository.save(new Consultorio(201, "2º Piso"));
    }
}
