package org.rishbootdev.healthsphere.controller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.service.PatientService;
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

    @PostMapping("/create")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.updatePatient(patientDto));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.deletePatient(patientId));
    }

    @PutMapping("/{patientId}/assign-doctor/{doctorId}")
    public ResponseEntity<String> assignDoctorToPatient(@PathVariable String patientId, @PathVariable String doctorId) {
        return ResponseEntity.ok(patientService.assignDoctorToPatient(patientId, doctorId));
    }

    @PutMapping("/{patientId}/remove-doctor")
    public ResponseEntity<String> removeDoctorFromPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.removeDoctorFromPatient(patientId));
    }

    @PutMapping("/{patientId}/assign-hospital/{hospitalId}")
    public ResponseEntity<String> assignHospitalToPatient(@PathVariable String patientId, @PathVariable String hospitalId) {
        return ResponseEntity.ok(patientService.assignHospitalToPatient(patientId, hospitalId));
    }

    @PutMapping("/{patientId}/remove-hospital")
    public ResponseEntity<String> removeHospitalFromPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.removeHospitalFromPatient(patientId));
    }

    @GetMapping("/{patientId}/reports")
    public ResponseEntity<List<LabReportDto>> getReportsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.getReportsByPatient(patientId));
    }

}


