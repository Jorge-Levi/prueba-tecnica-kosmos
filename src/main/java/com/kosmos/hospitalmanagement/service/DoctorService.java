package com.kosmos.hospitalmanagement.service;

import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@Service
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private Validator validator;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor saveDoctor(Doctor doctor) throws Exception {
        if (doctor == null) {
            logger.warn("Intento de guardar un doctor nulo");
            throw new Exception("El objeto doctor no puede ser nulo");
        }

        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Doctor> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            logger.warn("Errores de validación al guardar doctor: {}", sb.toString());
            throw new Exception(sb.toString());
        }

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            logger.warn("Intento de eliminar un doctor inexistente con id {}", id);
            return;
        }
        doctorRepository.deleteById(id);
    }
}
