package org.rishbootdev.healthsphere.service;

import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.hyperledger.fabric.client.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabService {

    private final FabricGatewayService fabricGatewayService;

    public LabService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }

    public LabReportDto uploadReport(LabReportDto report) {
        try {
            Contract contract = fabricGatewayService.getContract();

            String reportJson = JsonUtils.toJson(report);

            contract.submitTransaction("CreateLabReport", reportJson);

            return report;
        } catch (Exception e) {
            throw new ChainCodeException("Error uploading lab report: " + e.getMessage());
        }
    }

    public List<LabReportDto> getReportsByPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();

            byte[] result = contract.evaluateTransaction("GetReportsByPatientId", patientId);

            return JsonUtils.fromJsonList(new String(result), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve reports for patient: " + e.getMessage());
        }
    }

    public List<LabReportDto> getReportsByLabId(String labId) {
        try {
            Contract contract = fabricGatewayService.getContract();

            byte[] result = contract.evaluateTransaction("GetReportsByLab", labId);

            return JsonUtils.fromJsonList(new String(result), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve reports for lab: " + e.getMessage());
        }
    }

    public List<LabReportDto> getAllReports() {
        try {
            Contract contract = fabricGatewayService.getContract();

            byte[] result = contract.evaluateTransaction("GetAllLabReports");

            return JsonUtils.fromJsonList(new String(result), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve all lab reports: " + e.getMessage());
        }
    }

    public String deleteReport(String reportId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("DeleteLabReport", reportId);
            return "Lab report deleted successfully with ID: " + reportId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete lab report: " + e.getMessage());
        }
    }

    public String updateReport(LabReportDto report) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String reportJson = JsonUtils.toJson(report);
            contract.submitTransaction("UpdateLabReport", reportJson);
            return "Lab report updated successfully: " + report.getReportId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update lab report: " + e.getMessage());
        }
    }
}
