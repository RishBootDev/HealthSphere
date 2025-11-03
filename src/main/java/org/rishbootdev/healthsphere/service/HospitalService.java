package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.DoctorDto;
import org.rishbootdev.healthsphere.dto.HospitalDto;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.dto.LabDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final FabricGatewayService fabricGatewayService;


    public String registerHospital(HospitalDto hospitalDto) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String hospitalJson = JsonUtils.toJson(hospitalDto);
            return new String(contract.submitTransaction("registerHospital", hospitalJson));
        } catch (Exception e) {
            throw new ChainCodeException("Failed to register hospital: " + e.getMessage());
        }
    }

    public HospitalDto createHospital(String hospitalId, String name, String address, String license) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("createHospital", hospitalId, name, address, license);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create hospital: " + e.getMessage());
        }
    }

    public HospitalDto getHospitalById(String hospitalId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getHospitalById", hospitalId);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to fetch hospital: " + e.getMessage());
        }
    }

    public List<HospitalDto> getAllHospitals() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getAllHospitals");
            return JsonUtils.fromJsonList(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching all hospitals: " + e.getMessage());
        }
    }

    public HospitalDto updateHospital(String hospitalId, String name, String address) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("updateHospital", hospitalId, name, address);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update hospital: " + e.getMessage());
        }
    }

    public String deleteHospitalById(String hospitalId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("deleteHospitalById", hospitalId);
            return "Hospital deleted successfully with ID: " + hospitalId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete hospital: " + e.getMessage());
        }
    }

    public HospitalDto addDoctorToHospital(String hospitalId, String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("addDoctorToHospital", hospitalId, doctorId);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add doctor to hospital: " + e.getMessage());
        }
    }

    public HospitalDto addPatientToHospital(String hospitalId, String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("addPatientToHospital", hospitalId, patientId);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add patient to hospital: " + e.getMessage());
        }
    }

    public HospitalDto addRecordToHospital(String hospitalId, String recordId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("addRecordToHospital", hospitalId, recordId);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add record to hospital: " + e.getMessage());
        }
    }

    public HospitalDto addLabToHospital(String hospitalId, String labId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("addLabToHospital", hospitalId, labId);
            return JsonUtils.fromJson(new String(result), HospitalDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add lab to hospital: " + e.getMessage());
        }
    }

    public List<DoctorDto> getDoctorsByHospital(String hospitalId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getDoctorsByHospital", hospitalId);
            return JsonUtils.fromJsonList(new String(result), DoctorDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching doctors by hospital: " + e.getMessage());
        }
    }

    public List<PatientDto> getPatientsByHospital(String hospitalId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getPatientsByHospital", hospitalId);
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching patients by hospital: " + e.getMessage());
        }
    }

    public List<RecordDto> getRecordsByHospital(String hospitalId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getRecordsByHospital", hospitalId);
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching records by hospital: " + e.getMessage());
        }
    }

    public List<LabDto> getAllLabs() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getAllLabs");
            return JsonUtils.fromJsonList(new String(result), LabDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching labs: " + e.getMessage());
        }
    }

    public List<PatientDto> getHospitalPatients() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("getHospitalPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching hospital patients: " + e.getMessage());
        }
    }
}
