package org.rishbootdev.healthsphere.controller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.MedicineDto;
import org.rishbootdev.healthsphere.dto.PharmaDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.service.AiService;
import org.rishbootdev.healthsphere.service.MedicineService;
import org.rishbootdev.healthsphere.service.PharmaService;
import org.rishbootdev.healthsphere.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/pharma")
@PreAuthorize("hasRole('PHARMA')")
@CrossOrigin
@RequiredArgsConstructor
public class PharmaController {

    private final PharmaService pharmaService;
    private final PrescriptionService prescriptionService;
    private final MedicineService medicineService;
    private final AiService aiService;


    @GetMapping("/getPharma/{pharmaId}")
    public ResponseEntity<PharmaDto> getPharmaById(String pharmaId){
        return ResponseEntity.ok(pharmaService.getPharma(pharmaId));
    }

    @GetMapping("/medicines/{name}")
    public ResponseEntity<?> searchMedicines(@PathVariable String name) {
        return ResponseEntity.ok(pharmaService.searchMedicineByName(name));
    }


    @GetMapping("/getMedicine/{medicineId}")
    public ResponseEntity<MedicineDto> readMedicine(@PathVariable String medicineId) {
        return ResponseEntity.ok(medicineService.readMedicine(medicineId));
    }


    @GetMapping("/searchMedicine")
    public ResponseEntity<List<MedicineDto>> searchMedicineByName(@RequestParam String name) {
        return ResponseEntity.ok(medicineService.searchMedicineByName(name));
    }

    @PutMapping("/{medicineId}/stock")
    public ResponseEntity<MedicineDto> updateMedicineStock(@PathVariable String medicineId,
                                                           @RequestParam int newStock) {
        return ResponseEntity.ok(medicineService.updateMedicineStock(medicineId, newStock));
    }

    @PostMapping("/pharma/{pharmaId}/add/{medicineId}")
    public ResponseEntity<PharmaDto> addMedicineToPharma(@PathVariable String pharmaId,
                                                         @PathVariable String medicineId) {
        return ResponseEntity.ok(pharmaService.addMedicineToPharma(pharmaId, medicineId));
    }

    @PostMapping("/pharma/{pharmaId}/remove/{medicineId}")
    public ResponseEntity<PharmaDto> removeMedicineFromPharma(@PathVariable String pharmaId,
                                                              @PathVariable String medicineId) {
        return ResponseEntity.ok(pharmaService.removeMedicineFromPharma(pharmaId, medicineId));
    }

    @GetMapping("/pharma/{pharmaId}/medicines")
    public ResponseEntity<List<MedicineDto>> getMedicinesByPharma(@PathVariable String pharmaId) {
        return ResponseEntity.ok(pharmaService.getMedicinesByPharma(pharmaId));
    }
    @GetMapping("/medicine/{medicineId}")
    public ResponseEntity<MedicineDto> getMedicine(@PathVariable String medicineId) {
        return ResponseEntity.ok(medicineService.readMedicine(medicineId));
    }
    @GetMapping("/prescription/{patientId}")
    public ResponseEntity<PrescriptionDto> getPrescriptionByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(pharmaService.getPrescriptionByPatient(patientId));
    }


    @GetMapping("/{prescriptionId}/medicines")
    public ResponseEntity<List<MedicineDto>> getMedicinesForPrescription(@PathVariable String prescriptionId) {
        return ResponseEntity.ok(prescriptionService.getMedicinesForPrescription(prescriptionId));
    }

    @GetMapping("/chat/{query}")
    public ResponseEntity<Flux<String>> getResponseFromAI(@PathVariable String query){
        return ResponseEntity.ok(aiService.getResponseForPharmaCist(query));
    }
}

