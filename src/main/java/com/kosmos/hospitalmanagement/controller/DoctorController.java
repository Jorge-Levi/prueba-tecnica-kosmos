package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@Valid @RequestBody Doctor doctor) {
        try {
            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            return ResponseEntity.ok(savedDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor doctorDetails) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        doctor.setNombre(doctorDetails.getNombre());
        doctor.setApellidoPaterno(doctorDetails.getApellidoPaterno());
        doctor.setApellidoMaterno(doctorDetails.getApellidoMaterno());
        doctor.setEspecialidad(doctorDetails.getEspecialidad());
        try {
            Doctor updatedDoctor = doctorService.saveDoctor(doctor);
            return ResponseEntity.ok(updatedDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
