package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private List<String> prescriptionIds;
    @JsonIgnore
    private List<String> recordIds;
    @JsonIgnore
    private String doctorId;
    @JsonIgnore
    private String hospitalId;
}

