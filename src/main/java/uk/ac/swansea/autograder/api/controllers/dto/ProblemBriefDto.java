package uk.ac.swansea.autograder.api.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import uk.ac.swansea.autograder.api.entities.Problem;

@Data
public class ProblemBriefDto {
    private Long id;
    @NotBlank
    private String title;
    private Problem.Status status;
    private Long userId;
}
