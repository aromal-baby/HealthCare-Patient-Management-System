package com.healthcarepatientmanagement.model.sql;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private String gender;
    private String email;
    private String address;
    private String insuranceProvider;
    private String insuranceId;

    @Column(updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();
}
