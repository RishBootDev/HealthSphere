package org.rishbootdev.healthsphere.controller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final PrescriptionService prescriptionService;
    private final RecordService recordService;
    private final PatientService patientService;
    private final MedicineService medicineService;
    private final LabService labService;
    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    private final PharmaService pharmaService;

    @GetMapping("/allPrescriptions")
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PrescriptionDto>> searchPrescriptions(@RequestParam String keyword) {
        return ResponseEntity.ok(prescriptionService.searchPrescriptions(keyword));
    }
    @GetMapping("/allRecords")
    public ResponseEntity<List<RecordDto>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }


    @GetMapping("/allPatients")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/allMedicines")
    public ResponseEntity<List<MedicineDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @PutMapping("/updateMedicine")
    public ResponseEntity<MedicineDto> updateMedicine(@RequestBody MedicineDto medicineDto) {
        return ResponseEntity.ok(medicineService.updateMedicine(medicineDto));
    }

    @DeleteMapping("/deleteMedicine/{medicineId}")
    public ResponseEntity<String> deleteMedicine(@PathVariable String medicineId) {
        return ResponseEntity.ok(medicineService.deleteMedicine(medicineId));
    }

    @GetMapping("/allLabs")
    public ResponseEntity<List<LabDto>> getAllLabs() {
        return ResponseEntity.ok(labService.getAllLabs());
    }

    @GetMapping("/allReports")
    public ResponseEntity<List<LabReportDto>> getAllLabReports() {
        return ResponseEntity.ok(labService.getAllLabReports());
    }
    @PostMapping("/createMedicine")
    public ResponseEntity<MedicineDto> createMedicine(@RequestBody MedicineDto medicineDto) {
        String customId = "MED" + UUID.randomUUID().toString().substring(0,5);
        medicineDto.setId(customId);
        return ResponseEntity.ok(medicineService.createMedicine(medicineDto));
    }

    @GetMapping("/allHospitals")
    public ResponseEntity<List<HospitalDto>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/allDoctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/allPharmas")
    public ResponseEntity<List<PharmaDto>> getAllPharma() {
        return ResponseEntity.ok(pharmaService.getAllPharmas());
    }
}
