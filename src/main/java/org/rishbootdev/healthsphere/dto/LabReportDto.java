package org.rishbootdev.healthsphere.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabReportDto {
    private String reportId;
    private PatientDto patient;
    private String testType;
    private String testResult;
    private LabDto lab;
    private String testDate;
    private String remarks;
}

