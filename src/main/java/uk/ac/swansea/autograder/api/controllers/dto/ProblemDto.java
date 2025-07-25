package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemDto extends ProblemBriefDto {
    @NotBlank
    private String description;
}
