package uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProblemBriefDto {
    private Long id;
    @NotBlank
    private String title;
    private Integer status;
    private Long lecturerId;
}
