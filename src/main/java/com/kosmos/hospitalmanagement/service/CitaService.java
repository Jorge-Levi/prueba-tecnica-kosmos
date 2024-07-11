package com.kosmos.hospitalmanagement.service;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Cita getCitaById(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    public Cita saveCita(Cita cita) throws Exception {
        // Validar las reglas de negocio
        if (!isCitaValida(cita)) {
            throw new Exception("Las reglas de negocio no se cumplen.");
        }
        return citaRepository.save(cita);
    }

    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }

    public List<Cita> getCitasByConsultorioAndHorario(Consultorio consultorio, LocalDateTime horarioConsulta) {
        return citaRepository.findByConsultorioAndHorarioConsulta(consultorio, horarioConsulta);
    }

    public List<Cita> getCitasByDoctorAndHorario(Doctor doctor, LocalDateTime horarioConsulta) {
        return citaRepository.findByDoctorAndHorarioConsulta(doctor, horarioConsulta);
    }

    public List<Cita> getCitasByPacienteAndHorario(String nombrePaciente, LocalDateTime start, LocalDateTime end) {
        return citaRepository.findByNombrePacienteAndHorarioConsultaBetween(nombrePaciente, start, end);
    }

    public List<Cita> getCitasByDoctorAndHorarioEntre(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        return citaRepository.findByDoctorAndHorarioConsultaBetween(doctor, start, end);
    }

    private boolean isCitaValida(Cita cita) {
        LocalDateTime inicioDia = cita.getHorarioConsulta().toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);

        // Validar que no se puede agendar cita en el mismo consultorio a la misma hora
        if (!citaRepository.findByConsultorioAndHorarioConsulta(cita.getConsultorio(), cita.getHorarioConsulta()).isEmpty()) {
            return false;
        }

        // Validar que no se puede agendar cita para un mismo doctor a la misma hora
        if (!citaRepository.findByDoctorAndHorarioConsulta(cita.getDoctor(), cita.getHorarioConsulta()).isEmpty()) {
            return false;
        }

        // Validar que no se puede agendar cita para un paciente a la misma hora ni con menos de 2 horas de diferencia para el mismo día
        LocalDateTime start = cita.getHorarioConsulta().minusHours(2);
        LocalDateTime end = cita.getHorarioConsulta().plusHours(2);
        if (!citaRepository.findByNombrePacienteAndHorarioConsultaBetween(cita.getNombrePaciente(), start, end).isEmpty()) {
            return false;
        }

        // Validar que un doctor no puede tener más de 8 citas en el mismo día
        if (citaRepository.findByDoctorAndHorarioConsultaBetween(cita.getDoctor(), inicioDia, finDia).size() >= 8) {
            return false;
        }

        return true;
    }
}
