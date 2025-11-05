package org.rishbootdev.healthsphere.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalDto {

    private String hospitalId;
    private String name;
    private String address;
    private String license;
    private List<String> doctorIds;
    private List<String> patientIds;
    private List<String> recordId;
    private List<String> labIds;
}
