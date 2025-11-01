package org.rishbootdev.healthsphere.controller;

import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@PreAuthorize("hasRole('DOCTOR')")
@CrossOrigin
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/patient")
    public ResponseEntity<?> createPatientRecord(@RequestBody RecordDto record) {
        return ResponseEntity.ok(doctorService.createRecord(record));
    }

    @PutMapping("/patient/{recordId}")
    public ResponseEntity<?> updatePatientRecord(@PathVariable String recordId, @RequestBody RecordDto record) {
        return ResponseEntity.ok(doctorService.updatePatientRecord(recordId, record));
    }

    @PostMapping("/prescription")
    public ResponseEntity<?> uploadPrescription(@RequestBody PrescriptionDto prescription) {
        return ResponseEntity.ok(doctorService.uploadPrescription(prescription));
    }

    @GetMapping("/prescriptions/{patientId}")
    public ResponseEntity<?> getPrescriptionsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(doctorService.getPrescriptionsByPatient(patientId));
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getDoctorPatients() {
        return ResponseEntity.ok(doctorService.getPatients());
    }

    @GetMapping("/records/search")
    public ResponseEntity<?> searchRecords(@RequestParam String keyword) {
        return ResponseEntity.ok(doctorService.searchRecords(keyword));
    }

}
