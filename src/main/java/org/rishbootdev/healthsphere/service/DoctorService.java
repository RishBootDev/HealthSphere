package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.DoctorDto;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final FabricGatewayService fabricGatewayService;

    public String registerDoctor(DoctorDto doctor) {
        try {
            Contract contract = fabricGatewayService.getContract();
            return new String(contract.submitTransaction(
                    "RegisterDoctor",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getHospital().getHospitalId(),
                    doctor.getQualification(),
                    doctor.getContact()
            ));
        } catch (Exception e) {
            throw new ChainCodeException("Failed to register doctor: " + e.getMessage());
        }
    }

    public String createDoctor(DoctorDto doctor) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String doctorJson = JsonUtils.toJson(doctor);
            contract.submitTransaction("createDoctor", doctorJson);
            return "Doctor created successfully: " + doctor.getDoctorId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create doctor: " + e.getMessage());
        }
    }

    public DoctorDto getDoctorById(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getDoctorById", doctorId);
            return JsonUtils.fromJson(new String(result), DoctorDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching doctor: " + e.getMessage());
        }
    }

    public String updateDoctor(DoctorDto doctor) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String doctorJson = JsonUtils.toJson(doctor);
            contract.submitTransaction("updateDoctor", doctorJson);
            return "Doctor updated successfully: " + doctor.getDoctorId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update doctor: " + e.getMessage());
        }
    }

    public String deleteDoctor(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("deleteDoctor", doctorId);
            return "Doctor deleted successfully: " + doctorId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete doctor: " + e.getMessage());
        }
    }

    public List<DoctorDto> getAllDoctors() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getAllDoctorsFast");
            return JsonUtils.fromJsonList(new String(result), DoctorDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching all doctors: " + e.getMessage());
        }
    }

    public List<PatientDto> getPatientsByDoctor(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPatientsByDoctorFast", doctorId);
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching patients for doctor: " + e.getMessage());
        }
    }

    public List<RecordDto> getRecordsByDoctor(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetRecordsByDoctorFast", doctorId);
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching records for doctor: " + e.getMessage());
        }
    }

    public String addPatientToDoctor(String doctorId, String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("addPatientToDoctor", doctorId, patientId);
            return "Patient " + patientId + " added to doctor " + doctorId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add patient to doctor: " + e.getMessage());
        }
    }

    public String removePatientFromDoctor(String doctorId, String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("removePatientFromDoctor", doctorId, patientId);
            return "Patient " + patientId + " removed from doctor " + doctorId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove patient from doctor: " + e.getMessage());
        }
    }

    public String addRecordToDoctor(String doctorId, String recordId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("addRecordToDoctor", doctorId, recordId);
            return "Record " + recordId + " added to doctor " + doctorId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add record to doctor: " + e.getMessage());
        }
    }

    public String removeRecordFromDoctor(String doctorId, String recordId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("removeRecordFromDoctor", doctorId, recordId);
            return "Record " + recordId + " removed from doctor " + doctorId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove record from doctor: " + e.getMessage());
        }
    }

    public RecordDto createRecord(RecordDto record) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String recordJson = JsonUtils.toJson(record);
            contract.submitTransaction("createPatientRecord", recordJson);
            return record;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create record: " + e.getMessage());
        }
    }

    public List<PatientDto> getDoctorPatients(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPatientsByDoctor", doctorId);
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching patients: " + e.getMessage());
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

    public List<PatientDto> getPatients() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch patients: " + e.getMessage());
        }
    }

    public List<PrescriptionDto> getPrescriptionsByPatient(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionsByPatient", patientId);
            return JsonUtils.fromJsonList(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching prescriptions: " + e.getMessage());
        }
    }

    public String updatePatientRecord(String recordId, RecordDto record) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String recordJson = JsonUtils.toJson(record);
            contract.submitTransaction("updatePatientRecord", recordId, recordJson);
            return "Record updated successfully for record ID: " + recordId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update record: " + e.getMessage());
        }
    }

    public String uploadPrescription(PrescriptionDto prescription) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String prescriptionJson = JsonUtils.toJson(prescription);
            contract.submitTransaction("uploadPrescription", prescriptionJson);
            return "Prescription uploaded for patient ID: " + prescription.getPatient().getPatientId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to upload prescription: " + e.getMessage());
        }
    }
}
