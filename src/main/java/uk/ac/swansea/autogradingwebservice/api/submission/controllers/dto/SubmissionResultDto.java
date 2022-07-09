package uk.ac.swansea.autogradingwebservice.api.submission.controllers.dto;

import lombok.Data;

@Data
public class SubmissionResultDto {
    private String output;
    private String expectedOutput;
    private Boolean isValid;
}
