package com.example.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull @Min(0)
    @Column(nullable = false)
    private Integer age;

    @NotBlank @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String disease;

    @OneToOne
    @JoinColumn(name = "doctor_id", unique = true)
    private Doctor doctor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
