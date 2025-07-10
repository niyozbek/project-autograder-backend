package uk.ac.swansea.autograder.general.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionDto {
    @NotBlank
    private String language;
    @NotBlank
    private String version;
    @NotBlank
    private String filename;
    @NotBlank
    private String code;
    private String input;
    private String expectedOutput;
}
