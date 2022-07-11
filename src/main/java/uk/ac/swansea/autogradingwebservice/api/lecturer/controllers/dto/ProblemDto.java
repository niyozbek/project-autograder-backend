package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProblemDto {
    private Long id;
    @NotBlank
    private String description;
    private Integer status;
    private Long lecturer_id;
}
