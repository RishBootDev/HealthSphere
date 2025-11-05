package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineDto {
    private String name;
    private String id;
    private String manufacturer;
    private String dosage;
    private int stock;
    private  String expiryDate;
}

