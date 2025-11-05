package org.rishbootdev.healthsphere.controller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.service.HospitalService;
import org.rishbootdev.healthsphere.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hospital")
@PreAuthorize("hasRole('HOSPITAL')")
@RequiredArgsConstructor
@CrossOrigin
public class HospitalController {

    private final HospitalService hospitalService;
    private final RecordService recordService;

    @PostMapping("/register")
    public ResponseEntity<String> registerHospital(@RequestBody HospitalDto hospitalDto) {
        return ResponseEntity.ok(hospitalService.registerHospital(hospitalDto));
    }

    @PostMapping("/create")
    public ResponseEntity<HospitalDto> createHospital(@RequestParam String hospitalId,
                                                      @RequestParam String name,
                                                      @RequestParam String address,
                                                      @RequestParam String license) {
        return ResponseEntity.ok(hospitalService.createHospital(hospitalId, name, address, license));
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<HospitalDto> getHospitalById(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalById(hospitalId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<HospitalDto>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @PutMapping("/update")
    public ResponseEntity<HospitalDto> updateHospital(@RequestParam String hospitalId,
                                                      @RequestParam String name,
                                                      @RequestParam String address) {
        return ResponseEntity.ok(hospitalService.updateHospital(hospitalId, name, address));
    }

    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<String> deleteHospitalById(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.deleteHospitalById(hospitalId));
    }

    @PostMapping("/{hospitalId}/doctor/{doctorId}")
    public ResponseEntity<HospitalDto> addDoctorToHospital(@PathVariable String hospitalId,
                                                           @PathVariable String doctorId) {
        return ResponseEntity.ok(hospitalService.addDoctorToHospital(hospitalId, doctorId));
    }

    @PostMapping("/{hospitalId}/patient/{patientId}")
    public ResponseEntity<HospitalDto> addPatientToHospital(@PathVariable String hospitalId,
                                                            @PathVariable String patientId) {
        return ResponseEntity.ok(hospitalService.addPatientToHospital(hospitalId, patientId));
    }

    @PostMapping("/{hospitalId}/record/{recordId}")
    public ResponseEntity<HospitalDto> addRecordToHospital(@PathVariable String hospitalId,
                                                           @PathVariable String recordId) {
        return ResponseEntity.ok(hospitalService.addRecordToHospital(hospitalId, recordId));
    }

    @PostMapping("/{hospitalId}/lab/{labId}")
    public ResponseEntity<HospitalDto> addLabToHospital(@PathVariable String hospitalId,
                                                        @PathVariable String labId) {
        return ResponseEntity.ok(hospitalService.addLabToHospital(hospitalId, labId));
    }

    @GetMapping("/{hospitalId}/doctors")
    public ResponseEntity<List<DoctorDto>> getDoctorsByHospital(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.getDoctorsByHospital(hospitalId));
    }

    @GetMapping("/{hospitalId}/patients")
    public ResponseEntity<List<PatientDto>> getPatientsByHospital(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.getPatientsByHospital(hospitalId));
    }

    @GetMapping("/{hospitalId}/records")
    public ResponseEntity<List<RecordDto>> getRecordsByHospital(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.getRecordsByHospital(hospitalId));
    }

    @GetMapping("/labs")
    public ResponseEntity<List<LabDto>> getAllLabs() {
        return ResponseEntity.ok(hospitalService.getAllLabs());
    }

    @GetMapping("/{hospitalId}/hospital-patients")
    public ResponseEntity<List<PatientDto>> getHospitalPatients(@PathVariable String hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalPatients(hospitalId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecordDto>> searchRecords(@RequestParam String keyword) {
        return ResponseEntity.ok(recordService.searchRecords(keyword));
    }

    @PostMapping("/create")
    public ResponseEntity<RecordDto> createRecord(@RequestBody RecordDto recordDto) {
        return ResponseEntity.ok(recordService.createPatientRecord(recordDto));
    }

    @PutMapping("/update/{recordId}")
    public ResponseEntity<String> updateRecord(@PathVariable String recordId, @RequestBody RecordDto recordDto) {
        return ResponseEntity.ok(recordService.updatePatientRecord(recordId, recordDto));
    }

    @DeleteMapping("/delete/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable String recordId) {
        return ResponseEntity.ok(recordService.deletePatientRecord(recordId));
    }
}
