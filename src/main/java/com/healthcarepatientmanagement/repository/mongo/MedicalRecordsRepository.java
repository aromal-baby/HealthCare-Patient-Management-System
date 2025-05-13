package com.healthcarepatientmanagement.repository.mongo;

import com.healthcarepatientmanagement.model.mongo.MedicalRecord;
import com.healthcarepatientmanagement.model.sql.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordsRepository extends MongoRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientIdOrderByDateCreatedDesc(Long patientId);

    Optional<MedicalRecord> findById(String id);

    void deleteById(String id);
}
