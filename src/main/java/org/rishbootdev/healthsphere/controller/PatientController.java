package org.rishbootdev.healthsphere.controller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/patient")
@PreAuthorize("hasRole('PATIENT')")
@CrossOrigin
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final LabService labService;
    private final PrescriptionService prescriptionService;
    private final AiService aiService;

    @GetMapping("/getPatient/{patientId}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.updatePatient(patientDto));
    }

    @DeleteMapping("/deletePatient/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.deletePatient(patientId));
    }

    @GetMapping("/{patientId}/reports")
    public ResponseEntity<List<LabReportDto>> getReportsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.getReportsByPatient(patientId));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<LabReportDto> getLabReport(@PathVariable String reportId) {
        return ResponseEntity.ok(labService.readLabReport(reportId));
    }

    @GetMapping(value = "/chat/{query}")
    public String getResponseFromAI(@PathVariable String query) {
        return aiService.getResponseForPatient(query).toString();
    }

    @GetMapping("/getPrescriptions/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsForPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }
}
