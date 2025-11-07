package org.rishbootdev.healthsphere.controller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.LabDto;
import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.service.LabService;
import org.rishbootdev.healthsphere.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lab")
@PreAuthorize("hasRole('LAB')")
@CrossOrigin
@RequiredArgsConstructor
public class LabDiagnosticsController {

    private final LabService labService;
    private final PatientService patientService;


    @GetMapping("/getLab/{labId}")
    public ResponseEntity<LabDto> getLab(@PathVariable String labId) {
        return ResponseEntity.ok(labService.readLab(labId));
    }

    @PutMapping("/updateLab/{labId}")
    public ResponseEntity<LabDto> updateLab(@PathVariable String labId, @RequestParam String newName) {
        return ResponseEntity.ok(labService.updateLab(labId, newName));
    }

    @PostMapping("/hospital/{hospitalId}/add/{labId}")
    public ResponseEntity<String> addLabToHospital(@PathVariable String hospitalId, @PathVariable String labId) {
        return ResponseEntity.ok(labService.addLabToHospital(hospitalId, labId));
    }


    @PostMapping("/createLabReport")
    public ResponseEntity<LabReportDto> createLabReport(@RequestBody LabReportDto reportDto) {
        String customId = "REP" + UUID.randomUUID().toString().substring(0,5);
        reportDto.setReportId(customId);
        return ResponseEntity.ok(labService.createLabReport(reportDto));
    }

    @GetMapping("/labReport/{reportId}")
    public ResponseEntity<LabReportDto> getLabReport(@PathVariable String reportId) {
        return ResponseEntity.ok(labService.readLabReport(reportId));
    }

    @PutMapping("/updateReport")
    public ResponseEntity<LabReportDto> updateLabReport(@RequestBody LabReportDto reportDto) {
        return ResponseEntity.ok(labService.updateLabReport(reportDto));
    }

    @DeleteMapping("/deleteReport/{reportId}")
    public ResponseEntity<String> deleteLabReport(@PathVariable String reportId) {
        return ResponseEntity.ok(labService.deleteLabReport(reportId));
    }


    @GetMapping("/reports/patient/{patientId}")
    public ResponseEntity<List<LabReportDto>> getReportsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(labService.getReportsByPatient(patientId));
    }

    @PostMapping("/{labId}/add-report/{reportId}")
    public ResponseEntity<String> addReportToLab(@PathVariable String labId, @PathVariable String reportId) {
        return ResponseEntity.ok(labService.addReportToLab(labId, reportId));
    }
    @PutMapping("/{patientId}/link-report/{reportId}")
    public ResponseEntity<String> linkReportToPatient(@PathVariable String patientId, @PathVariable String reportId) {
        return ResponseEntity.ok(patientService.linkReportToPatient(patientId, reportId));
    }

    @PutMapping("/{patientId}/unlink-report")
    public ResponseEntity<String> unlinkReportFromPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.unlinkReportFromPatient(patientId));
    }
}

