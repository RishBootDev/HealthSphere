package org.rishbootdev.healthsphere.service;

import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.hyperledger.fabric.client.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final FabricGatewayService fabricGatewayService;

    public DoctorService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }

    public RecordDto createRecord(RecordDto record) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("CreateRecord",
                    record.getRecordId(),
                    record.getPatient().getPatientId(),
                    record.getDoctor().getDoctorId(),
                    record.getDiagnosis(),
                    record.getTreatment(),
                    record.getVisitDate(),
                    record.getRemarks());
            return record;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create record: " + e.getMessage());
        }
    }


    public List<PatientDto> getDoctorPatients(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPatientsByDoctor", doctorId);
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching patients: " + e.getMessage());
        }
    }

    public List<RecordDto> searchRecords(String keyword) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("SearchRecords", keyword);
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to search records: " + e.getMessage());
        }
    }

    public List<PatientDto> getPatients() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch patients: " + e.getMessage());
        }
    }

    public PrescriptionDto getPrescriptionsByPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPrescriptionByPatient", patientId);
            return JsonUtils.fromJson(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching prescription: " + e.getMessage());
        }
    }

    public String updatePatientRecord(String recordId, RecordDto record) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("UpdateRecord",
                    recordId,
                    record.getDiagnosis(),
                    record.getTreatment(),
                    record.getVisitDate(),
                    record.getRemarks());
            return "Record updated successfully for record ID: " + recordId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update record: " + e.getMessage());
        }
    }

    public String uploadPrescription(PrescriptionDto prescription) {
        try {
            Contract contract = fabricGatewayService.getContract();

            String prescriptionJson = JsonUtils.toJson(prescription);
            contract.submitTransaction("CreatePrescription", prescriptionJson);

            return "Prescription uploaded for patient ID: " + prescription.getPatient().getPatientId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to upload prescription: " + e.getMessage());
        }
    }
}
