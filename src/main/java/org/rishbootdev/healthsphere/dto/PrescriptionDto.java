package org.rishbootdev.healthsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
    private String prescriptionId;
    private PatientDto patient;
    private DoctorDto doctor;
    private List<MedicineDto> medicines;
    private String dateIssued;
    private String remarks;
}

