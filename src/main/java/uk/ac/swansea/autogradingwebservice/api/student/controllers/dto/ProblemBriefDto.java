package uk.ac.swansea.autogradingwebservice.api.student.controllers.dto;

import lombok.Data;

@Data
public class ProblemBriefDto {
    private Long id;
    private String title;
    private Integer status;
    private Long lecturerId;
}
