package uk.ac.swansea.autograder.api.student.controllers.dto;

import lombok.Data;
import uk.ac.swansea.autograder.api.lecturer.entities.Problem;

@Data
public class ProblemBriefDto {
    private Long id;
    private String title;
    private Problem.Status status;
    private Long lecturerId;
}
