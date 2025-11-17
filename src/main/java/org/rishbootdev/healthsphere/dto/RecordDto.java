package org.rishbootdev.healthsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private String recordId;
    private String patientId;
    private String doctorId;
    private String hospitalId;
    private String diagnosis;
    private String treatment;
    private String remarks;
    private String visitDate;

}

