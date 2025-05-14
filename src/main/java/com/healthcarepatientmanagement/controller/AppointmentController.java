package com.healthcarepatientmanagement.controller;

import com.healthcarepatientmanagement.model.sql.Appointment;
import com.healthcarepatientmanagement.model.sql.MedicalStaff;
import com.healthcarepatientmanagement.model.sql.Patient;
import com.healthcarepatientmanagement.service.AppointmentService;
import com.healthcarepatientmanagement.service.MedicalStaffService;
import com.healthcarepatientmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final MedicalStaffService staffService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 PatientService patientService,
                                 MedicalStaffService staffService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.staffService = staffService;
    }
    
    // Form for adding the Appointment det
    @GetMapping("/add")
    public String showAddForm(Model model, @RequestParam(required = false) Long patientId) {
        Appointment appointment = new Appointment();
        
        // If patientId is provided pre-select the patient
        if (patientId != null) {
            patientService.getPatientById(patientId).ifPresent(appointment::setPatient);
        }
        
        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("staff", staffService.getAllMedicalStaff());
        
        return "appointment/add";
    }
    
    // Adding the appointment to the database
    @PostMapping("/add")
    public String addAppointment(@ModelAttribute Appointment appointment,
                                 @RequestParam Long patientId,
                                 @RequestParam Long staffId,
                                 RedirectAttributes redirectAttributes) {

        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalStaff staff = staffService.getStaffById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        appointment.setPatient(patient);
        appointment.setStaff(staff);

        // Checking for time conflicts
        if (appointmentService.hasTimeConflict(appointment)) {
            redirectAttributes.addFlashAttribute("error", "Time conflict detected. Please select another time.");
            return "redirect:/appointments/add?patientId=" + patientId;
        }

        appointmentService.saveAppointment(appointment);
        redirectAttributes.addFlashAttribute("success", "Appointment scheduled successfully.");

        return "redirect:/patients/" + patientId;
    }

    // To find the appointments by ID
    @GetMapping("/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {

        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointment", appointment);

        return "appointment/view";
    }

    // To update the appointment status
    @PostMapping("/{id}/update-status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {

        appointmentService.updateAppointmentStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Appointment status updated");
        return "redirect:/appointments/" + id;
    }

    // To show the calenders as there is view for available date & timeslots
    @GetMapping("/calender")
    public String calender(Model model,
                           @RequestParam(required = false) Long staffId,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }

        model.addAttribute("date", date);
        model.addAttribute("staffId", staffService.getAllMedicalStaff());
        model.addAttribute("selectedStaffId", staffId);

        if (staffId != null) {
            List<Appointment> availableSlots = appointmentService.getAvailableSlots(staffId, date);
            model.addAttribute("availableSlots", availableSlots);
        }

        return "appointment/calender";
    }

    // To delete the scheduled appointment
    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Long patientId = appointment.getPatient().getPatientId();

        appointmentService.deleteAppointment(id);
        redirectAttributes.addFlashAttribute("success", "Appointment canceled successfully");

        return "redirect:/patients/" + patientId;
    }
}
