package org.rishbootdev.healthsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private String recordId;
    private PatientDto patient;
    private DoctorDto doctor;
    private String diagnosis;
    private String treatment;
    private String remarks;
    private String visitDate;

}

