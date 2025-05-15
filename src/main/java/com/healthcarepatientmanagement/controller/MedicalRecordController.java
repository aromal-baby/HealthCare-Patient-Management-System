package com.healthcarepatientmanagement.controller;

import com.healthcarepatientmanagement.model.mongo.MedicalRecord;
import com.healthcarepatientmanagement.model.mongo.TestResult;
import com.healthcarepatientmanagement.model.sql.MedicalStaff;
import com.healthcarepatientmanagement.model.sql.Patient;
import com.healthcarepatientmanagement.service.MedicalRecordService;
import com.healthcarepatientmanagement.service.MedicalStaffService;
import com.healthcarepatientmanagement.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final PatientService patientService;
    private final MedicalStaffService staffService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService,
                                   PatientService patientService,
                                   MedicalStaffService staffService) {

        this.medicalRecordService = medicalRecordService;
        this.patientService = patientService;
        this.staffService = staffService;
    }

    /*
            Medical Records endpoint
     */

    // For getting all the medical records
    @GetMapping
    public String listRecords(Model model) {

        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("records", records);

        // Adding patient names for display
        Map<Long, String> patientNames = new HashMap<>();
        for (MedicalRecord record : records) {
            patientService.getPatientById(record.getPatientId()).ifPresent(patient -> {
                patientNames.put(record.getPatientId(), patient.getFirstName() + " " + patient.getLastName());
            });
        }

        return "medical-records/list";
    }


    // For the Medical records adding form
    @GetMapping("/add")
    public String showAddForm(Model model, @RequestParam(required = false) Long patientId) {

        MedicalRecord medicalRecord = new MedicalRecord();

        // If the patient ID is provided, pre-select the patient
        if (patientId != null) {
            medicalRecord.setPatientId(patientId);

            // Adding patient name for display
            patientService.getPatientById(patientId).ifPresent(patient -> {
                model.addAttribute("patientName", patient.getFirstName() + " " + patient.getLastName());
            });
        }

        medicalRecord.setVitalSigns(new HashMap<>());
        medicalRecord.setSymptoms(new ArrayList<>());

        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("staff", staffService.getAllMedicalStaff());
        model.addAttribute("selectedPatientId", patientId);

        return "medical-records/add";
    }

    // Adding the medical records to the database
    @PostMapping("/add")
    public String addMedicalRecord(@ModelAttribute MedicalRecord medicalRecord,
                                   @RequestParam Long patientId,
                                   @RequestParam Long staffId,
                                   @RequestParam(value = "symptoms", required = false) String symptomStr,
                                   RedirectAttributes redirectAttributes) {

        medicalRecord.setRecordId("MR" + UUID.randomUUID().toString().substring(0, 8));
        medicalRecord.setPatientId(patientId);
        medicalRecord.setCreatedBy(staffId);
        medicalRecord.setDateCreated(LocalDateTime.now());

        // Processing symptoms coma seperated string
        if (symptomStr != null && !symptomStr.trim().isEmpty()) {
            List<String> symptoms = Arrays.asList(symptomStr.split("\\s*,\\s*"));
            medicalRecord.setSymptoms(symptoms);
        }

        // Initializing maps if they're null
        if (medicalRecord.getVitalSigns() == null) {
            medicalRecord.setVitalSigns(new HashMap<>());
        }

        MedicalRecord savedRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
        redirectAttributes.addFlashAttribute("success", "Medical record added successfully");

        return "redirect:/patients/" + patientId;
    }


    // For getting records by ID
    @GetMapping("/{id}")
    public String viewMedicalRecord(@PathVariable String id, Model model) {

        MedicalRecord record = medicalRecordService.getMedicalRecordById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));

        Patient patient = patientService.getPatientById(record.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalStaff staff = staffService.getStaffById(record.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        model.addAttribute("record", record);
        model.addAttribute("patient", patient);
        model.addAttribute("staff", staff);

        return "medical-records/view";
    }

    // Getting the form for editing the records
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        MedicalRecord record = medicalRecordService.getMedicalRecordById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));

        model.addAttribute("medicalRecord", record);
        model.addAttribute("staff", staffService.getAllMedicalStaff());

        // Adding patient name for the display
        patientService.getPatientById(record.getPatientId()).ifPresent(patient -> {
            model.addAttribute("patientName",
                    patient.getFirstName() + " " + patient.getLastName());
        });

        // converting the converted list of symptoms to comma-seperated string fro form
        if (record.getSymptoms() != null && !record.getSymptoms().isEmpty()) {
            model.addAttribute("symptomsString", String.join(",", record.getSymptoms()));
        }

        return "medical-records/edit";
    }

    // For implementing the edit (UPDATE)
    @PostMapping("/{id}/edit")
    public String updateMedicalRecord(@PathVariable String id,
                                      @ModelAttribute MedicalRecord medicalRecord,
                                      @RequestParam Long staffId,
                                      @RequestParam(value = "symptoms", required = false) String symptomStr,
                                      RedirectAttributes redirectAttributes) {

        MedicalRecord existingRecord = medicalRecordService.getMedicalRecordById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));

        // Updating the fields but preserving original ID, patient and creation date
        medicalRecord.setId(existingRecord.getId());
        medicalRecord.setRecordId(existingRecord.getRecordId());
        medicalRecord.setPatientId(existingRecord.getPatientId());
        medicalRecord.setDateCreated(existingRecord.getDateCreated());
        medicalRecord.setCreatedBy(staffId);

        // Processing the records from comma-seperated string
        if (symptomStr != null && !symptomStr.trim().isEmpty()) {
            List<String> symptoms = Arrays.asList(symptomStr.split("\\s*,\\s*"));
            medicalRecord.setSymptoms(symptoms);
        }

        medicalRecordService.saveMedicalRecord(medicalRecord);
        redirectAttributes.addFlashAttribute("success", "Medical record updated successfully");

        return "redirect:/medical-records/" + id;
    }


    // For deleting the patients Record
    @PostMapping("/{id}/delete")
    public String deleteMedicalRecord(@PathVariable String id, RedirectAttributes redirectAttributes) {

        MedicalRecord record = medicalRecordService.getMedicalRecordById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));

        Long patientId = record.getPatientId();

        medicalRecordService.deleteMedicalRecord(id);
        redirectAttributes.addFlashAttribute("success", "Medical record deleted successfully");

        return "redirect:/patients/" + patientId;
    }


    /*
            Test Result Endpoints
     */

    // For getting all the Test results
    public String listTestResults(Model model) {

        List<TestResult> testResults = medicalRecordService.getAllTestResults();
        model.addAttribute("testResults", testResults);

        // Adding patient names for display
        Map<Long, String> patientNames = new HashMap<>();
        for (TestResult result : testResults) {
            patientService.getPatientById(result.getPatientId()).ifPresent(patient -> {
                patientNames.put(result.getPatientId(),
                        patient.getFirstName() + " " + patient.getLastName());
            });
        }
        model.addAttribute("patientNames", patientNames);

        return "medical-records/test-results/list";
    }

    // For the form for adding the results
    @GetMapping("/test-results/add")
    public String showAddTestResultForm(Model model, @RequestParam(required = false) Long patientId) {

        TestResult testResult = new TestResult();

        // If patientId is provided, pre-select the patient
        if (patientId != null) {
            testResult.setPatientId(patientId);

            // Adding patient name for display
            patientService.getPatientById(patientId).ifPresent(patient -> {
                model.addAttribute("patientName", patient.getFirstName() + " " + patient.getLastName());
            });
        }

        testResult.setDatePerformed(LocalDateTime.now());
        testResult.setResults(new HashMap<>());
        testResult.setAbnormalFlags(new ArrayList<>());

        model.addAttribute("testResult", testResult);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("staff", staffService.getAllMedicalStaff());
        model.addAttribute("selectedPatientId", patientId);

        return "medical-records/test-results/add";
    }

    // For actually adding the test results
    public String addTestResult(@ModelAttribute TestResult testResult,
                                @RequestParam Long patientId,
                                @RequestParam Long staffId,
                                RedirectAttributes redirectAttributes) {

        testResult.setTestId("TR" + UUID.randomUUID().toString().substring(0, 8));
        testResult.setPatientId(patientId);
        testResult.setOrderedBy(staffId);

        // Initializing maps and list if they are null
        if (testResult.getResults() == null) {
            testResult.setResults(new HashMap<>());
        }

        if (testResult.getAbnormalFlags() == null) {
            testResult.setAbnormalFlags(new ArrayList<>());
        }

        medicalRecordService.saveTestResult(testResult);
        redirectAttributes.addFlashAttribute("success", "Test result added successfully");

        return "redirect:/patients/" + patientId;
    }

    // For reading the test results
    public String viewTestResults(@PathVariable String id, Model model) {
        TestResult testResult = medicalRecordService.getTestResultById(id)
                .orElseThrow(() -> new RuntimeException("TestResult not found"));

        Patient patient = patientService.getPatientById(testResult.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalStaff staff = staffService.getStaffById(testResult.getOrderedBy())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        model.addAttribute("testResult", testResult);
        model.addAttribute("patient", patient);
        model.addAttribute("staff", staff);

        return "medical-records/test-results/view";
    }

    // Search functionality
    @GetMapping("/search")
    public String searchRecords(@RequestParam String query, Model model) {
        List<MedicalRecord> searchResults = medicalRecordService.searchMedicalRecords(query);
        model.addAttribute("records", searchResults);
        model.addAttribute("searchQuery", query);

        // Adding patient names for display
        Map<Long, String> patientNames = new HashMap<>();
        for (MedicalRecord record : searchResults) {
            patientService.getPatientById(record.getPatientId()).ifPresent(patient -> {
                patientNames.put(record.getPatientId(),
                        patient.getFirstName() + " " + patient.getLastName());
            });
        }
        model.addAttribute("patientNames", patientNames);

        return "medical-records/search-results";
    }
}
