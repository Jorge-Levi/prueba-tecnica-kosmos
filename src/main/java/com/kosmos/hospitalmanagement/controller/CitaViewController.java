package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Cita;
import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.service.CitaService;
import com.kosmos.hospitalmanagement.service.ConsultorioService;
import com.kosmos.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String showCitaForm(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("doctores", doctorService.getAllDoctors());
        model.addAttribute("consultorios", consultorioService.getAllConsultorios());
        return "cita-form";
    }

    @PostMapping("/cita/save")
    public String saveCita(@RequestParam("consultorioId") Long consultorioId,
                           @RequestParam("doctorId") Long doctorId,
                           @ModelAttribute Cita cita) {
        Consultorio consultorio = consultorioService.getConsultorioById(consultorioId);
        Doctor doctor = doctorService.getDoctorById(doctorId);
        cita.setConsultorio(consultorio);
        cita.setDoctor(doctor);
        try {
            citaService.saveCita(cita);
            return "redirect:/citas";
        } catch (Exception e) {
            return "redirect:/cita/form?error=" + e.getMessage();
        }
    }


    @GetMapping("/cita/delete")
    public String deleteCita(@RequestParam Long id) {
        citaService.deleteCita(id);
        return "redirect:/citas";
    }
}
