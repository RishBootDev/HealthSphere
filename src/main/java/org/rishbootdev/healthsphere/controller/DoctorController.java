package org.rishbootdev.healthsphere.controller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.DoctorService;
import org.rishbootdev.healthsphere.service.PrescriptionService;
import org.rishbootdev.healthsphere.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@PreAuthorize("hasRole('DOCTOR')")
@CrossOrigin
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final PrescriptionService prescriptionService;
    private final RecordService recordService;


    @PostMapping("/patient")
    public ResponseEntity<RecordDto> createPatientRecord(@RequestBody RecordDto record) {
        return ResponseEntity.ok(doctorService.createRecord(record));
    }

    @PutMapping("/patient/{recordId}")
    public ResponseEntity<String> updatePatientRecord(@PathVariable String recordId,
                                                      @RequestBody RecordDto record) {
        return ResponseEntity.ok(doctorService.updatePatientRecord(recordId, record));
    }

    @PostMapping("/prescription")
    public ResponseEntity<String> uploadPrescription(@RequestBody PrescriptionDto prescription) {
        return ResponseEntity.ok(doctorService.uploadPrescription(prescription));
    }

    @GetMapping("/prescriptions/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(doctorService.getPrescriptionsByPatient(patientId));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(doctorService.getPatients());
    }

    @GetMapping("/records/search")
    public ResponseEntity<List<RecordDto>> searchRecords(@RequestParam String keyword) {
        return ResponseEntity.ok(doctorService.searchRecords(keyword));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.registerDoctor(doctorDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorDto));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.deleteDoctor(doctorId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientDto>> getPatientsByDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.getPatientsByDoctor(doctorId));
    }

    @GetMapping("/{doctorId}/records")
    public ResponseEntity<List<RecordDto>> getRecordsByDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.getRecordsByDoctor(doctorId));
    }

    @PostMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<String> addPatientToDoctor(@PathVariable String doctorId,
                                                     @PathVariable String patientId) {
        return ResponseEntity.ok(doctorService.addPatientToDoctor(doctorId, patientId));
    }

    @DeleteMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<String> removePatientFromDoctor(@PathVariable String doctorId,
                                                          @PathVariable String patientId) {
        return ResponseEntity.ok(doctorService.removePatientFromDoctor(doctorId, patientId));
    }

    @PostMapping("/{doctorId}/records/{recordId}")
    public ResponseEntity<String> addRecordToDoctor(@PathVariable String doctorId,
                                                    @PathVariable String recordId) {
        return ResponseEntity.ok(doctorService.addRecordToDoctor(doctorId, recordId));
    }

    @DeleteMapping("/{doctorId}/records/{recordId}")
    public ResponseEntity<String> removeRecordFromDoctor(@PathVariable String doctorId,
                                                         @PathVariable String recordId) {
        return ResponseEntity.ok(doctorService.removeRecordFromDoctor(doctorId, recordId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPrescription(@RequestBody PrescriptionDto prescriptionDto) {
        return ResponseEntity.ok(prescriptionService.createPrescription(prescriptionDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable("id") String prescriptionId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(prescriptionId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePrescription(@RequestBody PrescriptionDto prescriptionDto) {
        return ResponseEntity.ok(prescriptionService.updatePrescription(prescriptionDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable("id") String prescriptionId) {
        return ResponseEntity.ok(prescriptionService.deletePrescription(prescriptionId));
    }

    @PostMapping("/{prescriptionId}/add-medicine/{medicineId}")
    public ResponseEntity<String> addMedicineToPrescription(
            @PathVariable String prescriptionId,
            @PathVariable String medicineId) {
        prescriptionService.addMedicineToPrescription(prescriptionId, medicineId);
        return ResponseEntity.ok("Medicine added to prescription successfully");
    }

    @DeleteMapping("/{prescriptionId}/remove-medicine/{medicineId}")
    public ResponseEntity<String> removeMedicineFromPrescription(
            @PathVariable String prescriptionId,
            @PathVariable String medicineId) {
        prescriptionService.removeMedicineFromPrescription(prescriptionId, medicineId);
        return ResponseEntity.ok("Medicine removed from prescription successfully");
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctor(doctorId));
    }

}
