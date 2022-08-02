package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto;

import lombok.Data;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;

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
    private Submission.Status status;
    private Integer grade;
}
