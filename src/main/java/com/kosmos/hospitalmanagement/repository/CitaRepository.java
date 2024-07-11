package com.kosmos.hospitalmanagement.repository;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByConsultorioAndHorarioConsulta(Consultorio consultorio, LocalDateTime horarioConsulta);
    List<Cita> findByDoctorAndHorarioConsulta(Doctor doctor, LocalDateTime horarioConsulta);
    List<Cita> findByNombrePacienteAndHorarioConsultaBetween(String nombrePaciente, LocalDateTime start, LocalDateTime end);
    List<Cita> findByDoctorAndHorarioConsultaBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);
}
