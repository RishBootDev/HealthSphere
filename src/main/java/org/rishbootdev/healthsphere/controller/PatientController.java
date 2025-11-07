package org.rishbootdev.healthsphere.controller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.service.AiService;
import org.rishbootdev.healthsphere.service.LabService;
import org.rishbootdev.healthsphere.service.PatientService;
import org.rishbootdev.healthsphere.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @GetMapping("/chat/{query}")
    public ResponseEntity<Flux<String>> getResponseFromAI(@PathVariable String query){
        return ResponseEntity.ok(aiService.getResponseForPatient(query));
    }

    @GetMapping("/getPrescriptions/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsForPatient(@PathVariable String patientId){
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }

}


