package org.rishbootdev.healthsphere.dto;

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
    private HospitalDto hospital;
    private List<PatientDto> patientDtoList;
    private List<RecordDto> recordDtos;
    private String qualification;
    private String contact;
}

