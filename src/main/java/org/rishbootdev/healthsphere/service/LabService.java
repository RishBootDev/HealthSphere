package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.LabDto;
import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabService {

    private final FabricGatewayService fabricGatewayService;

    public Contract getLabContract(){
        return fabricGatewayService.getContract("LabContract");
    }

    public String createLab(LabDto lab) {
        try {
            Contract contract = getLabContract();
            contract.submitTransaction("createLab", lab.getLabId(), lab.getName(), lab.getHospitalId());
            return "Lab created successfully with ID: " + lab.getLabId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create lab: " + e.getMessage());
        }
    }

    public LabDto readLab(String labId) {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.evaluateTransaction("readLab", labId);
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), LabDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch lab details: " + e.getMessage());
        }
    }

    public LabDto updateLab(String labId, String newName) {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.submitTransaction("updateLab", labId, newName);
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), LabDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update lab: " + e.getMessage());
        }
    }

    public String deleteLab(String labId) {
        try {
            Contract contract =getLabContract();
            contract.submitTransaction("deleteLab", labId);
            return "Lab deleted successfully with ID: " + labId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete lab: " + e.getMessage());
        }
    }

    public List<LabDto> getAllLabs() {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.evaluateTransaction("getAllLabs");
            System.out.println(new String(result,StandardCharsets.UTF_8));
            return JsonUtils.fromJsonList(new String(result,StandardCharsets.UTF_8), LabDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve all labs: " + e.getMessage());
        }
    }

    public LabReportDto createLabReport(LabReportDto report) {
        try {
            Contract contract = getLabContract();
            contract.submitTransaction(
                    "createLabReport",
                    report.getReportId(),
                    report.getPatientId(),
                    report.getTestType(),
                    report.getTestResult(),
                    report.getLabId(),
                    report.getTestDate(),
                    report.getRemarks()
            );
            return report;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create lab report: " + e.getMessage());
        }
    }

    public LabReportDto readLabReport(String reportId) {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.evaluateTransaction("readLabReport", reportId);
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to read lab report: " + e.getMessage());
        }
    }

    public LabReportDto updateLabReport(LabReportDto report) {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.submitTransaction(
                    "updateLabReport",
                    report.getReportId(),
                    report.getTestType(),
                    report.getTestResult(),
                    report.getTestDate(),
                    report.getRemarks()
            );
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), LabReportDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update lab report: " + e.getMessage());
        }
    }

    public String deleteLabReport(String reportId) {
        try {
            Contract contract = getLabContract();
            contract.submitTransaction("deleteLabReport", reportId);
            return "Lab report deleted successfully with ID: " + reportId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete lab report: " + e.getMessage());
        }
    }

    public List<LabReportDto> getAllLabReports() {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.evaluateTransaction("getAllLabReports");
            return JsonUtils.fromJsonList(new String(result,StandardCharsets.UTF_8), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve lab reports: " + e.getMessage());
        }
    }

    public List<LabReportDto> getReportsByPatient(String patientId) {
        try {
            Contract contract = getLabContract();
            byte[] result = contract.evaluateTransaction("getReportsByPatient", patientId);
            return JsonUtils.fromJsonList(new String(result, StandardCharsets.UTF_8), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to retrieve reports for patient: " + e.getMessage());
        }
    }

    public String addReportToLab(String labId, String reportId) {
        try {
            Contract contract = getLabContract();
            contract.submitTransaction("addReportToLab", labId, reportId);
            return "Report " + reportId + " added to Lab " + labId + " successfully.";
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add report to lab: " + e.getMessage());
        }
    }

    public String addLabToHospital(String hospitalId, String labId) {
        try {
            Contract contract = getLabContract();
            contract.submitTransaction("addLabToHospital", hospitalId, labId);
            return "Lab " + labId + " linked successfully to Hospital " + hospitalId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add lab to hospital: " + e.getMessage());
        }
    }
}
