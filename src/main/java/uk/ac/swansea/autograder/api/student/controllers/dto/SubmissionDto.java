package uk.ac.swansea.autograder.api.student.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubmissionDto extends SubmissionBriefDto{
    @NotBlank
    private String code;
}
