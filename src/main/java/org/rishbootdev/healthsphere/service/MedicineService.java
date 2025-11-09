package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.dto.MedicineDto;
import org.rishbootdev.healthsphere.dto.PharmaDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final FabricGatewayService fabricGatewayService;

    private Contract getMedicineContract(){
        return fabricGatewayService.getContract("MedicineContract");
    }

    public MedicineDto createMedicine(MedicineDto medicine) {
        System.out.println(medicine.getName());
        System.out.println(medicine.getDosage());
        System.out.println(medicine.getManufacturer());
        try {
            Contract contract= getMedicineContract();
            String medicineJson = JsonUtils.toJson(medicine);
            contract.submitTransaction("createMedicine", medicineJson);
            return medicine;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create medicine: " + e.getMessage());
        }
    }

    public MedicineDto readMedicine(String medicineId) {
        try {
            Contract contract = getMedicineContract();
            byte[] result = contract.evaluateTransaction("readMedicine", medicineId);
            return JsonUtils.fromJson(new String(result, StandardCharsets.UTF_8), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error reading medicine: " + e.getMessage());
        }
    }

    public List<MedicineDto> getAllMedicines() {
        try {
            Contract contract = getMedicineContract();
            byte[] result = contract.evaluateTransaction("getAllMedicines");
            return JsonUtils.fromJsonList(new String(result,StandardCharsets.UTF_8), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch medicines: " + e.getMessage());
        }
    }

    public MedicineDto updateMedicine(MedicineDto medicine) {
        try {
            Contract contract = getMedicineContract();
            String medicineJson = JsonUtils.toJson(medicine);
            contract.submitTransaction("updateMedicine", medicineJson);
            return medicine;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update medicine: " + e.getMessage());
        }
    }

    public String deleteMedicine(String medicineId) {
        try {
            Contract contract = getMedicineContract();
            contract.submitTransaction("deleteMedicine", medicineId);
            return "Medicine deleted successfully: " + medicineId;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete medicine: " + e.getMessage());
        }
    }

    public List<MedicineDto> searchMedicineByName(String name) {
        try {
            Contract contract =getMedicineContract();
            byte[] result = contract.evaluateTransaction("searchMedicineByName", name);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return JsonUtils.fromJsonList(new String(result,StandardCharsets.UTF_8), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error searching medicine: " + e.getMessage());
        }
    }

    public MedicineDto updateMedicineStock(String medicineId, int newStock) {
        try {
            Contract contract =getMedicineContract();
            byte[] result = contract.submitTransaction("updateMedicineStock", medicineId, String.valueOf(newStock));
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), MedicineDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to update medicine stock: " + e.getMessage());
        }
    }

    public PharmaDto addMedicineToPharma(String pharmaId, String medicineId) {
        try {
            Contract contract = getMedicineContract();
            byte[] result = contract.submitTransaction("addMedicineToPharma", pharmaId, medicineId);
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), PharmaDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to add medicine to pharma: " + e.getMessage());
        }
    }

    public PharmaDto removeMedicineFromPharma(String pharmaId, String medicineId) {
        try {
            Contract contract = getMedicineContract();
            byte[] result = contract.submitTransaction("removeMedicineFromPharma", pharmaId, medicineId);
            return JsonUtils.fromJson(new String(result,StandardCharsets.UTF_8), PharmaDto.class);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to remove medicine from pharma: " + e.getMessage());
        }
    }

    public List<MedicineDto> getMedicinesByPharma(String pharmaId) {
        try {
            Contract contract = getMedicineContract();
            byte[] result = contract.evaluateTransaction("getMedicinesByPharma", pharmaId);
            return JsonUtils.fromJsonList(new String(result,StandardCharsets.UTF_8), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Error fetching medicines by pharma: " + e.getMessage());
        }
    }
}
