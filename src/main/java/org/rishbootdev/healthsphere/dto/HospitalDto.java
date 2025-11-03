package org.rishbootdev.healthsphere.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {

    private String hospitalId;
    private String name;
    private String address;
    private String license;
    private List<DoctorDto> doctorDtoList;
    private List<PatientDto> patientDtoList;
    private List<RecordDto> recordDtoList;
    private List<LabDto> labDtoList;
}
