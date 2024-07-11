package com.kosmos.hospitalmanagement.controller;

import com.kosmos.hospitalmanagement.model.Consultorio;
import com.kosmos.hospitalmanagement.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConsultorioViewController {

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping("/consultorios")
    public String getAllConsultorios(Model model) {
        model.addAttribute("consultorios", consultorioService.getAllConsultorios());
        return "consultorios";
    }

    @GetMapping("/consultorio/form")
    public String showConsultorioForm(Model model) {
        model.addAttribute("consultorio", new Consultorio());
        return "consultorio-form";
    }

    @PostMapping("/consultorio/save")
    public String saveConsultorio(@ModelAttribute Consultorio consultorio) {
        consultorioService.saveConsultorio(consultorio);
        return "redirect:/consultorios";
    }

    @GetMapping("/consultorio/delete")
    public String deleteConsultorio(@RequestParam Long id) {
        consultorioService.deleteConsultorio(id);
        return "redirect:/consultorios";
    }
}
