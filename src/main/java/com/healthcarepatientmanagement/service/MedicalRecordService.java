package com.healthcarepatientmanagement.service;

import com.healthcarepatientmanagement.model.mongo.MedicalRecord;
import com.healthcarepatientmanagement.model.mongo.TestResult;
import com.healthcarepatientmanagement.repository.mongo.MedicalRecordsRepository;
import com.healthcarepatientmanagement.repository.mongo.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordRepository;
    private final TestResultRepository testResultRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordsRepository medicalRecordsRepository, TestResultRepository testResultRepository) {
        this.medicalRecordRepository = medicalRecordsRepository;
        this.testResultRepository = testResultRepository;
    }

    /*
        Medical record methods
     */

    // Reading all records
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    // Reading records by id
    public Optional<MedicalRecord> getMedicalRecordById(String id) {
        return medicalRecordRepository.findById(id);
    }

    // Getting a patient's medical record
    public List<MedicalRecord> getPatientMedicalRecords(Long patientId) {
        return medicalRecordRepository.findByPatientIdOrderByDateCreatedDesc(patientId);
    }

    // Saving..
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        // Generate record ID if not provided
        if (medicalRecord.getRecordId() == null || medicalRecord.getRecordId().isEmpty()) {
            medicalRecord.setRecordId("MR" + UUID.randomUUID().toString().substring(0, 8));
        }
        return medicalRecordRepository.save(medicalRecord);
    }

    // Deleting..
    public void deleteMedicalRecord(String id) {
        medicalRecordRepository.deleteById(id);
    }


    /*
        Test Result Methods
     */

    // Getting all
    public List<TestResult> getAllTestResults() {
        return testResultRepository.findAll();
    }

    // getting by the ID
    public Optional<TestResult> getTestResultById(String id) {
        return testResultRepository.findById(id);
    }

    // Getting a patient's test result
    public List<TestResult> getPatientTestResults(Long patientId) {
        return testResultRepository.findByPatientIdOrderByDatePerformedDesc(patientId);
    }

    // Saving..
    public TestResult saveTestResult(TestResult testResult) {
        // Generate test ID if not provided
        if (testResult.getTestId() == null || testResult.getTestId().isEmpty()) {
            testResult.setTestId("TR" + UUID.randomUUID().toString().substring(0, 8));
        }
        return testResultRepository.save(testResult);
    }

    // Deleting..
    public void deleteTestResult(String id) {
        testResultRepository.deleteById(id);
    }

    // Additional business logic methods

    /**
     * Searches medical records by diagnosis, symptoms, or notes
     */
    public List<MedicalRecord> searchMedicalRecords(String query) {
        // In a real implementation, this would use MongoDB's text search capabilities
        // This is a placeholder
        return List.of();
    }

    /**
     * Retrieves statistics about diagnoses for reporting
     */
    public List<Object> getDiagnosisStatistics() {
        // In a real implementation, this would use MongoDB's aggregation framework
        // This is a placeholder
        return List.of();
    }

    /**
     * Retrieves integrated patient data combining information from both MySQL and MongoDB
     */
    public Map<String, Object> getIntegratedPatientData(Long patientId) {
        // This method would combine data from both databases
        // Placeholder implementation
        return new HashMap<>();
    }
}
