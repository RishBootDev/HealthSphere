package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private String doctorId;
    private String name;
    private String specialization;
    private String hospitalId;
    private List<String> patientId;
    private List<String> recordId;
    private String qualification;
    private String contact;
}

