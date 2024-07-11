package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Doctor;
import com.kosmos.hospitalmanagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctores";
    }

    @GetMapping("/doctor/delete")
    public String deleteDoctor(@RequestParam Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctores";
    }
}
