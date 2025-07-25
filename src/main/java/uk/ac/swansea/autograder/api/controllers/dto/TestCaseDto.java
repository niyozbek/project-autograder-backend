package uk.ac.swansea.autograder.api.controllers.dto;

import lombok.Data;

@Data
public class TestCaseDto {
    private Long id;
    private Long problemId;
    private String input;
    private String expectedOutput;
}
