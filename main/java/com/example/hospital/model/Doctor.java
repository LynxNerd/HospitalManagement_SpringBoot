package com.example.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String specialization;

    @OneToOne(mappedBy = "doctor")
    private Patient patient;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public Patient getPatient() { return patient; }
}
