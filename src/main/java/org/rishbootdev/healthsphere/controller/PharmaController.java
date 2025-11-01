package org.rishbootdev.healthsphere.controller;


import org.rishbootdev.healthsphere.service.PharmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharma")
@PreAuthorize("hasRole('PHARMA')")
@CrossOrigin()
public class PharmaController {

    private final PharmaService pharmaService;

    public PharmaController(PharmaService pharmaService) {
        this.pharmaService = pharmaService;
    }

    @GetMapping("/prescription/{patientId}")
    public ResponseEntity<?> getPrescription(@PathVariable String patientId) {
        return ResponseEntity.ok(pharmaService.getPrescription(patientId));
    }

    @PutMapping("/stock/{medicineId}")
    public ResponseEntity<?> updateStock(@PathVariable String medicineId,@RequestParam int stock) {
        return ResponseEntity.ok(pharmaService.updateMedicineStock(medicineId, stock));
    }

    @GetMapping("/medicines")
    public ResponseEntity<?> getAllMedicines() {
        return ResponseEntity.ok(pharmaService.getAllMedicines());
    }

    @GetMapping("/medicines/{name}")
    public ResponseEntity<?> searchMedicines(@PathVariable String name) {
        return ResponseEntity.ok(pharmaService.searchMedicineByName(name));
    }

}

