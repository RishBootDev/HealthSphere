package org.rishbootdev.healthsphere.service;

import org.rishbootdev.healthsphere.dto.MedicineDto;
import org.rishbootdev.healthsphere.dto.PrescriptionDto;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.rishbootdev.healthsphere.utility.JsonUtils;
import org.hyperledger.fabric.client.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmaService {

    private final FabricGatewayService fabricGatewayService;

    public PharmaService(FabricGatewayService fabricGatewayService) {
        this.fabricGatewayService = fabricGatewayService;
    }


    public List<MedicineDto> getAllMedicines() {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetAllMedicines");
            return JsonUtils.fromJsonList(new String(result), MedicineDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException("Unable to fetch medicines: " + e.getMessage());
        }
    }


    public MedicineDto searchMedicineByName(String name) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("SearchMedicineByName", name);
            List<MedicineDto> medicines = JsonUtils.fromJsonList(new String(result), MedicineDto.class);
            if (medicines.isEmpty()) {
                throw new LedgerAccessException("⚠️ No medicine found with name: " + name);
            }
            return medicines.get(0);
        } catch (Exception e) {
            throw new LedgerAccessException(" Error searching medicine: " + e.getMessage());
        }
    }


    public MedicineDto updateMedicineStock(String medicineId, int stock) {
        try {
            Contract contract = fabricGatewayService.getContract();
            contract.submitTransaction("UpdateMedicineStock", medicineId, String.valueOf(stock));

            byte[] updated = contract.evaluateTransaction("GetMedicine", medicineId);
            return JsonUtils.fromJson(new String(updated), MedicineDto.class);
        } catch (Exception e) {
            throw new ChainCodeException(" Failed to update medicine stock: " + e.getMessage());
        }
    }

    public PrescriptionDto getPrescription(String patientId) {
        try {
            Contract contract = fabricGatewayService.getContract();
            byte[] result = contract.evaluateTransaction("GetPrescriptionByPatient", patientId);
            return JsonUtils.fromJson(new String(result), PrescriptionDto.class);
        } catch (Exception e) {
            throw new LedgerAccessException(" Unable to fetch prescription: " + e.getMessage());
        }
    }
}
