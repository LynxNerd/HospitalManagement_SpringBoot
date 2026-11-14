package com.example.hospital.controller;

import com.example.hospital.model.Doctor;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorRepository doctors;
    private final PatientRepository patients;

    public DoctorController(DoctorRepository doctors, PatientRepository patients) {
        this.doctors = doctors;
        this.patients = patients;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("doctors", doctors.findAll());
        return "doctors/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("formAction", "/doctors");
        return "doctors/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Doctor doctor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/doctors");
            return "doctors/form";
        }
        doctors.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctors.findById(id).orElseThrow());
        model.addAttribute("formAction", "/doctors/" + id);
        return "doctors/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Doctor form,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            form.setId(id);
            model.addAttribute("formAction", "/doctors/" + id);
            return "doctors/form";
        }
        Doctor doctor = doctors.findById(id).orElseThrow();
        doctor.setName(form.getName());
        doctor.setSpecialization(form.getSpecialization());
        doctors.save(doctor);
        return "redirect:/doctors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        if (patients.existsByDoctor_IdAndIdNot(id, -1L)) {
            flash.addFlashAttribute("message", "Reassign the doctor's patient before deleting this doctor.");
        } else {
            doctors.deleteById(id);
            flash.addFlashAttribute("message", "Doctor deleted.");
        }
        return "redirect:/doctors";
    }
}
