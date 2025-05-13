package com.healthcarepatientmanagement.service;

import com.healthcarepatientmanagement.model.sql.Appointment;
import com.healthcarepatientmanagement.model.sql.Patient;
import com.healthcarepatientmanagement.repository.sql.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Reading all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // To read an appointment by Id
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // To read a patient's appointment
    public List<Appointment> getPatientAppointments(Patient patient) {
        return appointmentRepository.findByPatientOrderByAppointmentDateDescAppointmentTimeDesc(patient);
    }

    // Saving..
    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // To update the status of the patient's appointment
    @Transactional
    public void updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("appointment not found"));

        appointment.setStatus(status);
        // Saving the updated
        appointmentRepository.save(appointment);
    }

    // Deleting
    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Additional business logic methods

    /**
     * Checks if the requested appointment time conflicts with existing appointments
     * for either the patient or the medical staff
     */
    public boolean hasTimeConflict(Appointment appointment) {
        // Implementation would check for overlapping appointments
        // This is a simplified example
        return false;
    }

    /**
     * Returns available appointment slots for a specific doctor on a given date
     */
    public List<Appointment> getAvailableSlots(Long staffId, java.time.LocalDate date) {
        // Implementation would return available time slots
        // This is a placeholder
        return List.of();
    }
}
