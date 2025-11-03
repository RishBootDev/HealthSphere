package org.rishbootdev.healthsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private LabReportDto labReportDto;
    private String contact;
    private String bloodGroup;
    private String allergies;

    @JsonIgnore
    private List<PrescriptionDto> prescriptionDtoList;
    @JsonIgnore
    private List<RecordDto> recordDtoList;
    @JsonIgnore
    private DoctorDto doctorDto;
    @JsonIgnore
    private HospitalDto hospitalDto;
}

