package com.example.hospital.controller;

import com.example.hospital.model.Doctor;
import com.example.hospital.model.Patient;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private final PatientRepository patients;
    private final DoctorRepository doctors;

    public PatientController(PatientRepository patients, DoctorRepository doctors) {
        this.patients = patients;
        this.doctors = doctors;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("patients", patients.findAll());
        return "patients/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", doctors.findAll());
        model.addAttribute("formAction", "/patients");
        return "patients/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Patient patient, BindingResult result,
                         @RequestParam(required = false) Long doctorId, Model model,
                         RedirectAttributes flash) {
        Doctor doctor = resolveDoctor(doctorId, patient);
        if (doctorId != null && doctor == null) result.rejectValue("doctor", "invalid", "Choose a valid doctor.");
        if (doctor != null && patients.existsByDoctor_IdAndIdNot(doctorId, -1L)) {
            result.rejectValue("doctor", "assigned", "That doctor is already assigned to a patient.");
        }
        if (result.hasErrors()) return showForm(model, patient, "/patients");
        patient.setDoctor(doctor);
        patients.save(patient);
        flash.addFlashAttribute("message", "Patient added.");
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return showForm(model, patients.findById(id).orElseThrow(), "/patients/" + id);
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Patient form,
                         BindingResult result, @RequestParam(required = false) Long doctorId,
                         Model model, RedirectAttributes flash) {
        Patient current = patients.findById(id).orElseThrow();
        Doctor doctor = resolveDoctor(doctorId, form);
        if (doctorId != null && doctor == null) result.rejectValue("doctor", "invalid", "Choose a valid doctor.");
        if (doctor != null && patients.existsByDoctor_IdAndIdNot(doctorId, id)) {
            result.rejectValue("doctor", "assigned", "That doctor is already assigned to a patient.");
        }
        if (result.hasErrors()) {
            form.setId(id);
            return showForm(model, form, "/patients/" + id);
        }
        current.setName(form.getName());
        current.setAge(form.getAge());
        current.setDisease(form.getDisease());
        current.setDoctor(doctor);
        try {
            patients.save(current);
            flash.addFlashAttribute("message", "Patient updated.");
        } catch (DataIntegrityViolationException ex) {
            flash.addFlashAttribute("message", "That doctor is already assigned to another patient.");
        }
        return "redirect:/patients";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        patients.deleteById(id);
        flash.addFlashAttribute("message", "Patient deleted.");
        return "redirect:/patients";
    }

    private Doctor resolveDoctor(Long doctorId, Patient patient) {
        if (doctorId == null) return null;
        Doctor doctor = doctors.findById(doctorId).orElse(null);
        if (doctor != null) patient.setDoctor(doctor);
        return doctor;
    }

    private String showForm(Model model, Patient patient, String action) {
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctors.findAll());
        model.addAttribute("formAction", action);
        return "patients/form";
    }
}
