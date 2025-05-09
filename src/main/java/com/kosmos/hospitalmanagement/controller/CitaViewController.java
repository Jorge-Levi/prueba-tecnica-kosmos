package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.service.CitaService;
import com.kosmos.hospitalmanagement.service.ConsultorioService;
import com.kosmos.hospitalmanagement.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CitaViewController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping("/citas")
    public String getAllCitas(Model model) {
        model.addAttribute("citas", citaService.getAllCitas());
        return "citas";
    }

    @GetMapping("/cita/form")
    public String showCitaForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("doctores", doctorService.getAllDoctors());
        model.addAttribute("consultorios", consultorioService.getAllConsultorios());
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "cita-form";
    }

    @PostMapping("/cita/save")
    public String saveCita(@Valid @ModelAttribute Cita cita,
                           BindingResult bindingResult,
                           Model model) {

        // Cargar doctor y consultorio desde los IDs incluidos en Cita
        if (cita.getDoctor() != null && cita.getDoctor().getId() != null) {
            cita.setDoctor(doctorService.getDoctorById(cita.getDoctor().getId()));
        }
        if (cita.getConsultorio() != null && cita.getConsultorio().getId() != null) {
            cita.setConsultorio(consultorioService.getConsultorioById(cita.getConsultorio().getId()));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctores", doctorService.getAllDoctors());
            model.addAttribute("consultorios", consultorioService.getAllConsultorios());
            return "cita-form";
        }

        try {
            citaService.saveCita(cita);
            return "redirect:/citas";
        } catch (Exception e) {
            model.addAttribute("cita", cita);
            model.addAttribute("doctores", doctorService.getAllDoctors());
            model.addAttribute("consultorios", consultorioService.getAllConsultorios());
            model.addAttribute("errorMessage", e.getMessage());
            return "cita-form";
        }
    }

    @GetMapping("/cita/delete")
    public String deleteCita(@RequestParam Long id, Model model) {
        try {
            citaService.deleteCita(id);
            return "redirect:/citas";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al eliminar cita: " + e.getMessage());
            model.addAttribute("citas", citaService.getAllCitas());
            return "citas";
        }
    }
}
