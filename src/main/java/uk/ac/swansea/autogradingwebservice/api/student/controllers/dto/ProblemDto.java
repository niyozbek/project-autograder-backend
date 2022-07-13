package uk.ac.swansea.autogradingwebservice.api.student.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemDto extends ProblemBriefDto {
    private String description;
}
