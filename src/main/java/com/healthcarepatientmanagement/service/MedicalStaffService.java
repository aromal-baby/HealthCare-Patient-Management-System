package com.healthcarepatientmanagement.service;

import com.healthcarepatientmanagement.model.sql.MedicalStaff;
import com.healthcarepatientmanagement.repository.sql.MedicalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalStaffService {

    private final MedicalStaffRepository staffRepository;

    @Autowired
    public MedicalStaffService(MedicalStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Getting all
    public List<MedicalStaff> getAllMedicalStaff() {
        return staffRepository.findAll();
    }

    // Getting by ID
    public Optional<MedicalStaff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    // Saving..
    public MedicalStaff saveMedicalStaff(MedicalStaff staff) {
        return staffRepository.save(staff);
    }

    // Deleting..
    public void deleteMedicalStaff(Long id) {
        staffRepository.deleteById(id);
    }

    // Additional methods for finding staff by specialty, etc. can be added here

}
