package org.rishbootdev.healthsphere.service;

import org.rishbootdev.healthsphere.dto.DoctorDto;
import org.rishbootdev.healthsphere.dto.HospitalDto;
import org.rishbootdev.healthsphere.dto.PatientDto;
import org.rishbootdev.healthsphere.dto.RecordDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.hyperledger.fabric.client.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    private final FabricGatewayService fabricGatewayService;

    public HospitalService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }

    public String registerHospital(HospitalDto hospitalDto) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String hospitalJson = JsonUtils.toJson(hospitalDto);
            contract.submitTransaction("CreateHospital", hospitalJson);
            return "Hospital registered successfully with ID: "+hospitalDto.getHospitalId();
        } catch (Exception e) {
            throw new ChainCodeException("Failed to register hospital: "+e.getMessage());
        }
    }

    public DoctorDto registerDoctor(DoctorDto doctor) {
        try {
            Contract contract = fabricGatewayService.getContract();
            String doctorJson = JsonUtils.toJson(doctor);
            contract.submitTransaction("RegisterDoctor", doctorJson);
            return doctor;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to register doctor: "+e.getMessage());
        }
    }

    public List<DoctorDto> getAllDoctors() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllDoctors");
            return JsonUtils.fromJsonList(new String(result), DoctorDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch doctors: " + e.getMessage());
        }
    }

    public List<PatientDto> getAllPatients() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllPatients");
            return JsonUtils.fromJsonList(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch patients: " + e.getMessage());
        }
    }

    public DoctorDto getDoctorById(String doctorId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetDoctorById", doctorId);
            return JsonUtils.fromJson(new String(result), DoctorDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Doctor not found: " + e.getMessage());
        }
    }

    public PatientDto getPatientDetails(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPatientById", patientId);
            return JsonUtils.fromJson(new String(result), PatientDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Patient not found: " + e.getMessage());
        }
    }

    public List<RecordDto> getAllRecords() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllRecords");
            return JsonUtils.fromJsonList(new String(result), RecordDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch records: " + e.getMessage());
        }
    }
}
