package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;
import uk.ac.swansea.autograder.general.entities.Submission;

import jakarta.validation.constraints.NotBlank;

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
