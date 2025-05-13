package com.healthcarepatientmanagement.repository.sql;

import com.healthcarepatientmanagement.model.sql.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


}
