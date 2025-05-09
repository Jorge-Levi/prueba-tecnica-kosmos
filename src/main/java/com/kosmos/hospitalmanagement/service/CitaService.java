package com.kosmos.hospitalmanagement.service;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.repository.CitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    private static final Logger logger = LoggerFactory.getLogger(CitaService.class);

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Cita getCitaById(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cita saveCita(Cita cita) throws Exception {
        List<String> errores = validarCitaOptimizada(cita);
        if (!errores.isEmpty()) {
            String mensajeError = String.join(", ", errores);
            logger.warn("Error al guardar cita: {}", mensajeError);
            throw new Exception(mensajeError);
        }
        return citaRepository.save(cita);
    }

    private List<String> validarCitaOptimizada(Cita cita) {
        List<String> errores = new ArrayList<>();
        LocalDateTime inicioDia = cita.getHorarioConsulta().toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);
        LocalDateTime start = cita.getHorarioConsulta().minusHours(2);
        LocalDateTime end = cita.getHorarioConsulta().plusHours(2);

        // Consulta combinada
        List<Cita> conflictos = citaRepository.findConflictingCitas(
                cita.getConsultorio(), cita.getDoctor(), cita.getHorarioConsulta(),
                cita.getNombrePaciente(), start, end);

        for (Cita c : conflictos) {
            if (c.getConsultorio().equals(cita.getConsultorio()) && c.getHorarioConsulta().equals(cita.getHorarioConsulta())) {
                errores.add("El consultorio ya está ocupado a esa hora.");
            }
            if (c.getDoctor().equals(cita.getDoctor()) && c.getHorarioConsulta().equals(cita.getHorarioConsulta())) {
                errores.add("El doctor ya tiene una cita a esa hora.");
            }
            if (c.getNombrePaciente().equals(cita.getNombrePaciente()) &&
                    !c.getId().equals(cita.getId()) &&
                    c.getHorarioConsulta().isAfter(start) &&
                    c.getHorarioConsulta().isBefore(end)) {
                errores.add("El paciente tiene otra cita en el mismo horario o con menos de 2 horas de diferencia.");
            }
        }

        long citasDoctor = citaRepository.countByDoctorAndHorarioConsultaBetween(cita.getDoctor(), inicioDia, finDia);
        if (citasDoctor >= 8) {
            errores.add("El doctor ya tiene 8 citas asignadas en el día.");
        }

        return errores;
    }
    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }

}
