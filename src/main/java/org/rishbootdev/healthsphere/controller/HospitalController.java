package org.rishbootdev.healthsphere.controller;


import org.rishbootdev.healthsphere.dto.DoctorDto;
import org.rishbootdev.healthsphere.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
@PreAuthorize("hasRole('HOSPITAL')")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorDto doctor) {
        return ResponseEntity.ok(hospitalService.registerDoctor(doctor));
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(hospitalService.getAllDoctors());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getDoctorById(@PathVariable String doctorId) {
        return ResponseEntity.ok(hospitalService.getDoctorById(doctorId));
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getHospitalPatients() {
        return ResponseEntity.ok(hospitalService.getAllPatients());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPatientDetails(@PathVariable String patientId) {
        return ResponseEntity.ok(hospitalService.getPatientDetails(patientId));
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAllRecords() {
        return ResponseEntity.ok(hospitalService.getAllRecords());
    }

}
