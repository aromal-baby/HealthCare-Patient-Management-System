package com.healthcarepatientmanagement.service;

import com.healthcarepatientmanagement.model.mongo.PatientHistory;
import com.healthcarepatientmanagement.model.sql.Patient;
import com.healthcarepatientmanagement.repository.mongo.PatientHistoryRepository;
import com.healthcarepatientmanagement.repository.sql.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientHistoryRepository patientHistoryRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientHistoryRepository patientHistoryRepository) {
        this.patientRepository = patientRepository;
        this.patientHistoryRepository = patientHistoryRepository;
    }

    // For Reading all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // For Reading a single patient by Id
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // For Saving the patient data to the db
    @Transactional
    public Patient savePatient(Patient patient) {
        // Saving patient to MySQL
        Patient savedPatient = patientRepository.save(patient);

        // Create initial patient history in MongoDB
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setHistoryId("PH" + UUID.randomUUID().toString().substring(0, 8));
        patientHistory.setPatientId(savedPatient.getPatientId());
        patientHistoryRepository.save(patientHistory);

        return savedPatient;
    }

    // For Getting a patient's history
    public PatientHistory getPatientHistory(Long patientId) {
        return patientHistoryRepository.findByPatientId(patientId);
    }

    // Updating the history
    public void updatePatientHistory(PatientHistory patientHistory) {
        patientHistoryRepository.save(patientHistory);
    }

}
