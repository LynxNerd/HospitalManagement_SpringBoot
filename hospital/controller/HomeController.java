package com.example.hospital.controller;

import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final PatientRepository patients;
    private final DoctorRepository doctors;

    public HomeController(PatientRepository patients, DoctorRepository doctors) {
        this.patients = patients;
        this.doctors = doctors;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("patientCount", patients.count());
        model.addAttribute("doctorCount", doctors.count());
        return "index";
    }
}
