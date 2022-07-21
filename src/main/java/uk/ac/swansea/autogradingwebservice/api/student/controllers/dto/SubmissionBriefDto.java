package uk.ac.swansea.autogradingwebservice.api.student.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubmissionBriefDto {
    private Long id;
    private Long problemId;
    @NotBlank
    private String language;
    @NotBlank
    private String version;
    @NotBlank
    private String filename;
    private Long studentId;
}
