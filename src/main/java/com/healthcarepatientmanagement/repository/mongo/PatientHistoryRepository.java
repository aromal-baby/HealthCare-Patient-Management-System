package com.healthcarepatientmanagement.repository.mongo;

import com.healthcarepatientmanagement.model.mongo.PatientHistory;
import com.healthcarepatientmanagement.model.sql.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, Long> {

    PatientHistory findByPatientId(Long patientId);
}
