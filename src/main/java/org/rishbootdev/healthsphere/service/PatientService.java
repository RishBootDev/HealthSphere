package org.rishbootdev.healthsphere.service;

import org.rishbootdev.healthsphere.dto.LabReportDto;
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
public class PatientService {

    private final FabricGatewayService fabricGatewayService;

    public PatientService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }

    public PatientDto createPatient(PatientDto patient) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("CreatePatient",
                    patient.getPatientId(),
                    patient.getName(),
                    String.valueOf(patient.getAge()),
                    patient.getGender(),
                    patient.getAddress(),
                    patient.getContact(),
                    patient.getBloodGroup(),
                    patient.getAllergies()
            );
            return patient;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create patient: " + e.getMessage());
        }
    }

    public PatientDto getPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPatient", patientId);
            return JsonUtils.fromJson(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException(" Error retrieving patient: " + e.getMessage());
        }
    }

    public List<RecordDto> getPatientRecords(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllPatientRecords", patientId);
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException(" Unable to fetch patient records: " + e.getMessage());
        }
    }

    public LabReportDto getLabReport(String id) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetLabReportById", id);
            return JsonUtils.fromJson(new String(result), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch lab report: " + e.getMessage());
        }
    }

    public PrescriptionDto getPrescription(String id) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPrescriptionById", id);
            return JsonUtils.fromJson(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch prescription: " + e.getMessage());
        }
    }
}
