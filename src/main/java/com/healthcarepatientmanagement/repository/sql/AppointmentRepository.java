package com.healthcarepatientmanagement.repository.sql;

import com.healthcarepatientmanagement.model.sql.Appointment;
import com.healthcarepatientmanagement.model.sql.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    List<Appointment> findByPatientOrderByAppointmentDateDescAppointmentTimeDesc(Patient patient);
}
