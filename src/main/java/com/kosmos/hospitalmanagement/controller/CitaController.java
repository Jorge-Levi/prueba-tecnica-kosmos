package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        Cita cita = citaService.getCitaById(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cita);
    }

    @PostMapping
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.saveCita(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        Cita cita = citaService.getCitaById(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        cita.setConsultorio(citaDetails.getConsultorio());
        cita.setDoctor(citaDetails.getDoctor());
        cita.setHorarioConsulta(citaDetails.getHorarioConsulta());
        cita.setNombrePaciente(citaDetails.getNombrePaciente());
        try {
            Cita updatedCita = citaService.saveCita(cita);
            return ResponseEntity.ok(updatedCita);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        Cita cita = citaService.getCitaById(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public List<Cita> buscarCitas(@RequestParam(required = false) Long doctorId,
                                  @RequestParam(required = false) Long consultorioId,
                                  @RequestParam(required = false) LocalDateTime fecha) {
        // Aquí puedes añadir lógica para buscar citas por doctor, consultorio y fecha
        return citaService.getAllCitas(); // Placeholder, ajustar según la lógica
    }
}
