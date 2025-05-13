package com.healthcarepatientmanagement.model.mongo;

import jdk.jfr.Label;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "test_results")
public class TestResult {

    @Id
    private String id;

    private String testId;
    private Long patientId;
    private Long orderedBy;
    private String testType;
    private LocalDateTime datePerformed;
    private Map<String, Object> results;
    private String interpretation;
    private List<String> abnormalFlags;
}
