package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.MedicineDto;
import org.rishbootdev.healthsphere.dto.PharmaDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmaService {

    private final FabricGatewayService fabricGatewayService;

    private Contract getPharmaContract(){
        return fabricGatewayService.getContract("PharmaContract");
    }

    public MedicineDto createMedicine(MedicineDto medicineDto) {
        try {
            Contract contract = getPharmaContract();
            String medicineJson = JsonUtils.toJson(medicineDto);
            contract.submitTransaction("createMedicine", medicineJson);
            return medicineDto;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create medicine: " + e.getMessage());
        }
    }

    public MedicineDto getMedicine(String medicineId) {
        try {
            Contract contract = getPharmaContract();
            byte[] result = contract.evaluateTransaction("readMedicine", medicineId);
            return JsonUtils.fromJson(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch medicine: " + e.getMessage());
        }
    }

    public List<MedicineDto> getAllMedicines() {
        try {
            Contract contract = getPharmaContract();
            byte[] result = contract.evaluateTransaction("getAllMedicines");
            return JsonUtils.fromJsonList(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch all medicines: " + e.getMessage());
        }
    }

    public MedicineDto updateMedicine(MedicineDto medicineDto) {
        try {
            Contract contract = getPharmaContract();
            String medicineJson = JsonUtils.toJson(medicineDto);
            contract.submitTransaction("updateMedicine", medicineJson);

            byte[] updated = contract.evaluateTransaction("readMedicine", medicineDto.getId());
            return JsonUtils.fromJson(new String(updated), MedicineDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update medicine: " + e.getMessage());
        }
    }

    public MedicineDto updateMedicineStock(String medicineId, int stock) {
        try {
            Contract contract = getPharmaContract();
            contract.submitTransaction("updateMedicineStock", medicineId, String.valueOf(stock));

            byte[] updated = contract.evaluateTransaction("readMedicine", medicineId);
            return JsonUtils.fromJson(new String(updated), MedicineDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update stock: " + e.getMessage());
        }
    }

    public String deleteMedicine(String medicineId) {
        try {
            Contract contract =getPharmaContract();
            contract.submitTransaction("deleteMedicine", medicineId);
            return "Medicine deleted successfully: " + medicineId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete medicine: " + e.getMessage());
        }
    }

    public List<MedicineDto> searchMedicineByName(String name) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("searchMedicineByName", name);
            return JsonUtils.fromJsonList(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error searching medicine: " + e.getMessage());
        }
    }

    public PharmaDto addMedicineToPharma(String pharmaId, String medicineId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.submitTransaction("addMedicineToPharma", pharmaId, medicineId);
            return JsonUtils.fromJson(new String(result), PharmaDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add medicine to pharma: " + e.getMessage());
        }
    }
    public PharmaDto removeMedicineFromPharma(String pharmaId, String medicineId) {
        try {
            Contract contract = getPharmaContract();
            byte[] result = contract.submitTransaction("removeMedicineFromPharma", pharmaId, medicineId);
            return JsonUtils.fromJson(new String(result), PharmaDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove medicine from pharma: " + e.getMessage());
        }
    }

    public List<MedicineDto> getMedicinesByPharma(String pharmaId) {
        try {
            Contract contract = getPharmaContract();
            byte[] result = contract.evaluateTransaction("getMedicinesByPharma", pharmaId);
            return JsonUtils.fromJsonList(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch medicines by pharma: " + e.getMessage());
        }
    }

    public PrescriptionDto getPrescriptionByPatient(String patientId) {
        try {
            Contract contract = getPharmaContract();
            byte[] result = contract.evaluateTransaction("getPrescriptionsByPatient", patientId);
            return JsonUtils.fromJson(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch prescription: " + e.getMessage());
        }
    }
}
