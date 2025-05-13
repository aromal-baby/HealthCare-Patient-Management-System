package com.healthcarepatientmanagement.repository.mongo;

import com.healthcarepatientmanagement.model.mongo.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends MongoRepository<TestResult, Long> {

    List<TestResult> findByPatientIdOrderByDatePerformedDesc(Long patientId);

    Optional<TestResult> findById(String id);

    void deleteById(String id);
}
