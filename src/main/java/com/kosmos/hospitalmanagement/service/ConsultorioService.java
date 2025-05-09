package com.kosmos.hospitalmanagement.service;

import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.repository.ConsultorioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@Service
public class ConsultorioService {

    private static final Logger logger = LoggerFactory.getLogger(ConsultorioService.class);

    @Autowired
    private ConsultorioRepository consultorioRepository;

    @Autowired
    private Validator validator;

    public List<Consultorio> getAllConsultorios() {
        return consultorioRepository.findAll();
    }

    public Consultorio getConsultorioById(Long id) {
        return consultorioRepository.findById(id).orElse(null);
    }

    public Consultorio saveConsultorio(Consultorio consultorio) throws Exception {
        if (consultorio == null) {
            logger.warn("Intento de guardar un consultorio nulo");
            throw new Exception("El objeto consultorio no puede ser nulo");
        }

        Set<ConstraintViolation<Consultorio>> violations = validator.validate(consultorio);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Consultorio> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            logger.warn("Errores de validación al guardar consultorio: {}", sb.toString());
            throw new Exception(sb.toString());
        }

        return consultorioRepository.save(consultorio);
    }

    public void deleteConsultorio(Long id) {
        if (!consultorioRepository.existsById(id)) {
            logger.warn("Intento de eliminar un consultorio inexistente con id {}", id);
            return;
        }
        consultorioRepository.deleteById(id);
    }
}
