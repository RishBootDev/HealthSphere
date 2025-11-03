package org.rishbootdev.healthsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabDto {
    private String labId;
    private String name;
    private HospitalDto hospitalDto;
    private List<LabReportDto> reports;
}
