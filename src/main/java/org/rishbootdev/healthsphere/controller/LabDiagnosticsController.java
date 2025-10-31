package org.rishbootdev.healthsphere.controller;


import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.service.LabService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lab")
@PreAuthorize("hasRole('LAB')")
@CrossOrigin
public class LabDiagnosticsController {

    private final LabService labService;

    public LabDiagnosticsController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping("/Report/{reportId}")
    public ResponseEntity<?> getLabReport(@PathVariable String reportId) {
        return ResponseEntity.ok(labService.getReportsByLabId(reportId));
    }

    @PostMapping("/report")
    public ResponseEntity<?> uploadLabReport(@RequestBody LabReportDto report) {
        return ResponseEntity.ok(labService.uploadReport(report));
    }
}

