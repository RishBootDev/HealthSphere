package org.rishbootdev.healthsphere.controller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.MedicineService;
import org.rishbootdev.healthsphere.service.PatientService;
import org.rishbootdev.healthsphere.service.PrescriptionService;
import org.rishbootdev.healthsphere.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PrescriptionService prescriptionService;
    private final RecordService recordService;
    private final PatientService patientService;
    private final MedicineService medicineService;

    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PrescriptionDto>> searchPrescriptions(@RequestParam String keyword) {
        return ResponseEntity.ok(prescriptionService.searchPrescriptions(keyword));
    }
    @GetMapping("/all")
    public ResponseEntity<List<RecordDto>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }


    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/all")
    public ResponseEntity<List<MedicineDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineDto> updateMedicine(@RequestBody MedicineDto medicineDto) {
        return ResponseEntity.ok(medicineService.updateMedicine(medicineDto));
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<String> deleteMedicine(@PathVariable String medicineId) {
        return ResponseEntity.ok(medicineService.deleteMedicine(medicineId));
    }

    @GetMapping
    public ResponseEntity<List<LabDto>> getAllLabs() {
        return ResponseEntity.ok(labService.getAllLabs());
    }
    @DeleteMapping("/{labId}")
    public ResponseEntity<String> deleteLab(@PathVariable String labId) {
        return ResponseEntity.ok(labService.deleteLab(labId));
    }
}
