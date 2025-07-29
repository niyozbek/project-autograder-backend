package uk.ac.swansea.autograder.api.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import uk.ac.swansea.autograder.api.entities.Submission;

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
    private Long userId;
    private Submission.Status status;
    private Integer grade;
}
