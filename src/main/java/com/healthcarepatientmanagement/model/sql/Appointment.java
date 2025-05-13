package com.healthcarepatientmanagement.model.sql;

import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private MedicalStaff staff;

    @Column(nullable = false)
    private String appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    private String roomNumber;

    @Column(nullable = false)
    private String status = "scheduled";

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
