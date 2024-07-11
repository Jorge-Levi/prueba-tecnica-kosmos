package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping
    public List<Consultorio> getAllConsultorios() {
        return consultorioService.getAllConsultorios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> getConsultorioById(@PathVariable Long id) {
        Consultorio consultorio = consultorioService.getConsultorioById(id);
        if (consultorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultorio);
    }

    @PostMapping
    public Consultorio createConsultorio(@RequestBody Consultorio consultorio) {
        return consultorioService.saveConsultorio(consultorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> updateConsultorio(@PathVariable Long id, @RequestBody Consultorio consultorioDetails) {
        Consultorio consultorio = consultorioService.getConsultorioById(id);
        if (consultorio == null) {
            return ResponseEntity.notFound().build();
        }
        consultorio.setNumero(consultorioDetails.getNumero());
        consultorio.setPiso(consultorioDetails.getPiso());
        Consultorio updatedConsultorio = consultorioService.saveConsultorio(consultorio);
        return ResponseEntity.ok(updatedConsultorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultorio(@PathVariable Long id) {
        Consultorio consultorio = consultorioService.getConsultorioById(id);
        if (consultorio == null) {
            return ResponseEntity.notFound().build();
        }
        consultorioService.deleteConsultorio(id);
        return ResponseEntity.noContent().build();
    }
}
