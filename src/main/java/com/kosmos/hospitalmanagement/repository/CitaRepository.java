package com.kosmos.hospitalmanagement.repository;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByConsultorioAndHorarioConsulta(Consultorio consultorio, LocalDateTime horarioConsulta);
    List<Cita> findByDoctorAndHorarioConsulta(Doctor doctor, LocalDateTime horarioConsulta);
    List<Cita> findByNombrePacienteAndHorarioConsultaBetween(String nombrePaciente, LocalDateTime start, LocalDateTime end);
    List<Cita> findByDoctorAndHorarioConsultaBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

    long countByDoctorAndHorarioConsultaBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

    @Query("SELECT c FROM Cita c WHERE " +
            "(c.consultorio = :consultorio AND c.horarioConsulta = :horarioConsulta) OR " +
            "(c.doctor = :doctor AND c.horarioConsulta = :horarioConsulta) OR " +
            "(c.nombrePaciente = :nombrePaciente AND c.horarioConsulta BETWEEN :start AND :end)")
    List<Cita> findConflictingCitas(
            @Param("consultorio") Consultorio consultorio,
            @Param("doctor") Doctor doctor,
            @Param("horarioConsulta") LocalDateTime horarioConsulta,
            @Param("nombrePaciente") String nombrePaciente,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
