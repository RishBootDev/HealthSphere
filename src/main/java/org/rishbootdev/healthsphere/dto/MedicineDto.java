package org.rishbootdev.healthsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
    private String name;
    private String id;
    private String manufacturer;
    private String dosage;
    private int stock;
    private  String expiryDate;
}

