package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.LabReportDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final FabricGatewayService fabricGatewayService;

    private Contract getPatientContract(){
        return fabricGatewayService.getContract("PatientContract");
    }


    public PatientDto createPatient(PatientDto patient) {
        try {
            Contract contract = getPatientContract();
            String patientJson = JsonUtils.toJson(patient);
            contract.submitTransaction("createPatient", patientJson);
            return patient;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create patient: " + e.getMessage());
        }
    }

    // Retrieve a single patient
    public PatientDto getPatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            byte[] result = contract.evaluateTransaction("getPatient", patientId);
            return JsonUtils.fromJson(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error retrieving patient: " + e.getMessage());
        }
    }

    //  Update patient record
    public PatientDto updatePatient(PatientDto patient) {
        try {
            Contract contract = getPatientContract();
            String patientJson = JsonUtils.toJson(patient);
            contract.submitTransaction("updatePatient", patientJson);
            return patient;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update patient: " + e.getMessage());
        }
    }

    //  Delete a patient and their reports
    public String deletePatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("deletePatient", patientId);
            return "Patient deleted successfully: " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete patient: " + e.getMessage());
        }
    }

    //  Get all patients
    public List<PatientDto> getAllPatients() {
        try {
            Contract contract = getPatientContract();
            byte[] result = contract.evaluateTransaction("getAllPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching all patients: " + e.getMessage());
        }
    }

    //  Assign a doctor to a patient
    public String assignDoctorToPatient(String patientId, String doctorId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("assignDoctorToPatient", patientId, doctorId);
            return "Assigned doctor " + doctorId + " to patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to assign doctor: " + e.getMessage());
        }
    }

    //  Remove doctor from a patient
    public String removeDoctorFromPatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("removeDoctorFromPatient", patientId);
            return "Removed doctor from patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove doctor: " + e.getMessage());
        }
    }

    //  Assign hospital to patient
    public String assignHospitalToPatient(String patientId, String hospitalId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("assignHospitalToPatient", patientId, hospitalId);
            return "Assigned hospital " + hospitalId + " to patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to assign hospital: " + e.getMessage());
        }
    }

    //  Remove hospital from patient
    public String removeHospitalFromPatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("removeHospitalFromPatient", patientId);
            return "Removed hospital from patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove hospital: " + e.getMessage());
        }
    }
    public String linkReportToPatient(String patientId, String reportId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("linkReportToPatient", patientId, reportId);
            return "Linked report " + reportId + " to patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to link report: " + e.getMessage());
        }
    }

    public String unlinkReportFromPatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            contract.submitTransaction("unlinkReportFromPatient", patientId);
            return "Unlinked report from patient " + patientId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to unlink report: " + e.getMessage());
        }
    }

    public List<LabReportDto> getReportsByPatient(String patientId) {
        try {
            Contract contract = getPatientContract();
            byte[] result = contract.evaluateTransaction("getReportsByPatient", patientId);
            return JsonUtils.fromJsonList(new String(result), LabReportDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching reports for patient: " + e.getMessage());
        }
    }
}
