package org.rishbootdev.healthsphere.controller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.DoctorService;
import org.rishbootdev.healthsphere.service.PatientService;
import org.rishbootdev.healthsphere.service.PrescriptionService;
import org.rishbootdev.healthsphere.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
@PreAuthorize("hasRole('DOCTOR')")
@CrossOrigin
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final PrescriptionService prescriptionService;
    private final PatientService patientService;
    private final RecordService recordService;


    @PostMapping("/patient")
    public ResponseEntity<RecordDto> createPatientRecord(@RequestBody RecordDto record) {
        String customId = "REC" + UUID.randomUUID().toString().substring(0,5);
        record.setRecordId(customId);
        return ResponseEntity.ok(recordService.createPatientRecord(record));
    }

    @PutMapping("/patient/{recordId}")
    public ResponseEntity<String> updatePatientRecord(@PathVariable String recordId,
                                                      @RequestBody RecordDto record) {
        return ResponseEntity.ok(doctorService.updatePatientRecord(recordId, record));
    }

    @PostMapping("/prescription")
    public ResponseEntity<String> uploadPrescription(@RequestBody PrescriptionDto prescription) {
        String customId = "PRES" + UUID.randomUUID().toString().substring(0,5);
        return ResponseEntity.ok(doctorService.uploadPrescription(prescription));
    }

    @GetMapping("/prescriptions/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(doctorService.getPrescriptionsByPatient(patientId));
    }


    @GetMapping("/records/search")
    public ResponseEntity<List<RecordDto>> searchRecords(@RequestParam String keyword) {
        return ResponseEntity.ok(doctorService.searchRecords(keyword));
    }

    @GetMapping("/getDoctor/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @PutMapping("/updateDoctor")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorDto));
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
        String customId = "PRES" + UUID.randomUUID().toString().substring(0,5);
        prescriptionDto.setPrescriptionId(customId);
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
    @PutMapping("/{patientId}/remove-doctor")
    public ResponseEntity<String> removeDoctorFromPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(patientService.removeDoctorFromPatient(patientId));
    }
}
