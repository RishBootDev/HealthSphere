package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final FabricGatewayService fabricGatewayService;

    private Contract getRecordContract(){
        return fabricGatewayService.getContract("RecordContract");
    }


    public RecordDto createPatientRecord(RecordDto record) {
        try {
            Contract contract = getRecordContract();
            String recordJson = JsonUtils.toJson(record);
            contract.submitTransaction("createPatientRecord", recordJson);
            return record;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create record: " + e.getMessage());
        }
    }

    public String updatePatientRecord(String recordId, RecordDto record) {
        try {
            Contract contract = getRecordContract();
            String recordJson = JsonUtils.toJson(record);
            contract.submitTransaction("updatePatientRecord", recordId, recordJson);
            return "Record updated successfully: " + recordId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update record: " + e.getMessage());
        }
    }

    public String deletePatientRecord(String recordId) {
        try {
            Contract contract = getRecordContract();
            contract.submitTransaction("deletePatientRecord", recordId);
            return "Record deleted successfully: " + recordId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete record: " + e.getMessage());
        }
    }

    public List<RecordDto> getAllRecords() {
        try {
            Contract contract =getRecordContract();
            byte[] result = contract.evaluateTransaction("getAllRecords");
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to fetch records: " + e.getMessage());
        }
    }

    public List<PatientDto> getPatients() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to fetch patients: " + e.getMessage());
        }
    }

    public List<RecordDto> searchRecords(String keyword) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("searchRecords", keyword);
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to search records: " + e.getMessage());
        }
    }

    public List<PrescriptionDto> getPrescriptionsByPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionsByPatient", patientId);
            return JsonUtils.fromJsonList(new String(result, StandardCharsets.UTF_8), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to fetch prescriptions: " + e.getMessage());
        }
    }

    public String uploadPrescription(PrescriptionDto prescription) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String presJson = JsonUtils.toJson(prescription);
            contract.submitTransaction("uploadPrescription", presJson);
            return "Prescription uploaded for patient ID: " + prescription.getPatientId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to upload prescription: " + e.getMessage());
        }
    }
}
