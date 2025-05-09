package com.kosmos.hospitalmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita", indexes = {
        @Index(name = "idx_consultorio_horario", columnList = "consultorio_id, horarioConsulta"),
        @Index(name = "idx_doctor_horario", columnList = "doctor_id, horarioConsulta"),
        @Index(name = "idx_paciente_horario", columnList = "nombrePaciente, horarioConsulta")
})
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultorio_id", nullable = false)
    @NotNull(message = "El consultorio no puede ser nulo")
    private Consultorio consultorio;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "El doctor no puede ser nulo")
    private Doctor doctor;

    @NotNull(message = "La fecha y hora de la consulta es obligatoria")
    @Future(message = "La fecha y hora de la consulta debe ser en el futuro")
    private LocalDateTime horarioConsulta;

    @NotNull(message = "El nombre del paciente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre del paciente debe tener entre 2 y 100 caracteres")
    private String nombrePaciente;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(LocalDateTime horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
}
