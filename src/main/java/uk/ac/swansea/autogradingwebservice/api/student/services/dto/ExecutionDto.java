package uk.ac.swansea.autogradingwebservice.api.student.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    private String fileName;
    @NotBlank
    private String code;
    private String input;
    private String expectedOutput;
}
