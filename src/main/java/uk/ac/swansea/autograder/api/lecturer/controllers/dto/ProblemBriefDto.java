package uk.ac.swansea.autograder.api.lecturer.controllers.dto;

import lombok.Data;
import uk.ac.swansea.autograder.api.lecturer.entities.Problem;

import javax.validation.constraints.NotBlank;

@Data
public class ProblemBriefDto {
    private Long id;
    @NotBlank
    private String title;
    private Problem.Status status;
    private Long lecturerId;
}
