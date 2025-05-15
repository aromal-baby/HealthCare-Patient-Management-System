package com.healthcarepatientmanagement.repository.sql;

import com.healthcarepatientmanagement.model.sql.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, Long> {
}