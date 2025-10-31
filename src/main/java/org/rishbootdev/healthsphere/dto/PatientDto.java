package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String contact;
    private LabReportDto labReportDto;
    private String bloodGroup;
    private String allergies;

    @JsonIgnore
    private HospitalDto hospitalDto;
}

