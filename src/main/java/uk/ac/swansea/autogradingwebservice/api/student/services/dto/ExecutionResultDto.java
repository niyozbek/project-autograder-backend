package uk.ac.swansea.autogradingwebservice.api.student.services.dto;

import lombok.Data;

@Data
public class ExecutionResultDto {
    private String output;
    private String expectedOutput;
    private Boolean isValid;
}
