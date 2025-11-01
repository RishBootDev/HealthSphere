package org.rishbootdev.healthsphere.controller;


import org.rishbootdev.healthsphere.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@PreAuthorize("hasRole('PATIENT')")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}/prescriptions")
    public ResponseEntity<?> getPrescriptions(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPrescription(id));
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<?> getLabReports(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getLabReport(id));
    }
}


