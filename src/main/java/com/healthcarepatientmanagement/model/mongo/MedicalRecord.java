package com.healthcarepatientmanagement.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "medical_records")
public class MedicalRecord {

    @Id
    private String id;

    private String recordId;
    private Long patientId;
    private String recordType;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private Long createdBy;
    private Map<String, Object> vitalSigns;
    private List<String> symptoms;
    private String diagnosis;
    private String notes;
    private boolean followUpRequired;
}
