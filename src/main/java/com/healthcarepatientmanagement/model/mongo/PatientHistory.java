package com.healthcarepatientmanagement.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "patient_history")
public class PatientHistory {

    @Id
    private String id;

    private String historyId;
    private Long patientId;
    private List<Map<String, String>> allergies = new ArrayList<>();
    private List<String> chronicConditions = new ArrayList<>();
    private List<Map<String, Object>> surgeries = new ArrayList<>();
    private Map<String, List<String>> familyHistory = new HashMap<>();
    private Map<String, Object> socialHistory = new HashMap<>();
}
