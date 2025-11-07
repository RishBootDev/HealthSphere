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
public class PatientDto {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String labReportId;
    private String contact;
    private String bloodGroup;
    private String allergies;

    private List<String> prescriptionIds;
    private List<String> recordIds;
    private String doctorId;
    private String hospitalId;
}

