package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Doctor;
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
public class DoctorViewController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctores")
    public String getAllDoctors(Model model) {
        model.addAttribute("doctores", doctorService.getAllDoctors());
        return "doctores";
    }

    @GetMapping("/doctor/form")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    @PostMapping("/doctor/save")
    public String saveDoctor(@Valid @ModelAttribute Doctor doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "doctor-form";
        }
        try {
            doctorService.saveDoctor(doctor);
            return "redirect:/doctores";
        } catch (Exception e) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("errorMessage", e.getMessage());
            return "doctor-form";
        }
    }

    @GetMapping("/doctor/delete")
    public String deleteDoctor(@RequestParam Long id, Model model) {
        try {
            doctorService.deleteDoctor(id);
            return "redirect:/doctores";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al eliminar doctor: " + e.getMessage());
            model.addAttribute("doctores", doctorService.getAllDoctors());
            return "doctores";
        }
    }
}
