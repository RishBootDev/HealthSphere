package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescriptionDto {
    private String prescriptionId;
    private String patientId;
    private String doctorId;
    private List<String> medicineIds;
    private String issueDate;
    private String remarks;
}

