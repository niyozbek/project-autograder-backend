package uk.ac.swansea.autograder.api.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResultDto {
    private String output;
    private String expectedOutput;
    private Boolean isValid;
}
