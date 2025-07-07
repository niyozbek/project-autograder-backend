package uk.ac.swansea.autograder.api.lecturer.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemDto extends ProblemBriefDto {
    @NotBlank
    private String description;
}
