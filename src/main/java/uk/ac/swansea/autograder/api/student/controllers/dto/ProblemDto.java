package uk.ac.swansea.autograder.api.student.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemDto extends ProblemBriefDto {
    private String description;
}
