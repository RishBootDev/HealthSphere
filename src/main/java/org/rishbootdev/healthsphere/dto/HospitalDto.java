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
    private List<DoctorDto> doctorDtoList;
    private List<PatientDto> patientDtos;
    private List<RecordDto> recordDtos;
    private List<LabDto> labDtos;
}
