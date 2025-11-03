package org.rishbootdev.healthsphere.service;

import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.MedicineDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final FabricGatewayService fabricGatewayService;

    public PrescriptionService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }

    public String createPrescription(PrescriptionDto prescription) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String prescriptionJson = JsonUtils.toJson(prescription);
            byte[] result = contract.submitTransaction("createPrescription", prescriptionJson);
            return new String(result);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create prescription: " + e.getMessage());
        }
    }

    public PrescriptionDto getPrescriptionById(String prescriptionId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionById", prescriptionId);
            return JsonUtils.fromJson(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error retrieving prescription: " + e.getMessage());
        }
    }

    public List<PrescriptionDto> getAllPrescriptions() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getAllPrescriptions");
            return JsonUtils.fromJsonList(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching all prescriptions: " + e.getMessage());
        }
    }


    public String updatePrescription(PrescriptionDto prescription) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String prescriptionJson = JsonUtils.toJson(prescription);
            byte[] result = contract.submitTransaction("updatePrescription", prescriptionJson);
            return new String(result);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update prescription: " + e.getMessage());
        }
    }

    public String deletePrescription(String prescriptionId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("deletePrescription", prescriptionId);
            return new String(result);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete prescription: " + e.getMessage());
        }
    }

    public void addMedicineToPrescription(String prescriptionId, String medicineId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("addMedicineToPrescription", prescriptionId, medicineId);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add medicine to prescription: " + e.getMessage());
        }
    }


    public void removeMedicineFromPrescription(String prescriptionId, String medicineId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("removeMedicineFromPrescription", prescriptionId, medicineId);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove medicine from prescription: " + e.getMessage());
        }
    }

    public List<MedicineDto> getMedicinesForPrescription(String prescriptionId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getMedicinesForPrescription", prescriptionId);
            return JsonUtils.fromJsonList(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching medicines for prescription: " + e.getMessage());
        }
    }


    public List<PrescriptionDto> getPrescriptionsByPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionsByPatient", patientId);
            return JsonUtils.fromJsonList(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching prescriptions by patient: " + e.getMessage());
        }
    }

    public List<PrescriptionDto> getPrescriptionsByDoctor(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionsByDoctor", doctorId);
            return JsonUtils.fromJsonList(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching prescriptions by doctor: " + e.getMessage());
        }
    }

    public List<PrescriptionDto> searchPrescriptions(String keyword) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("searchPrescriptions", keyword);
            return JsonUtils.fromJsonList(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error searching prescriptions: " + e.getMessage());
        }
    }
}
