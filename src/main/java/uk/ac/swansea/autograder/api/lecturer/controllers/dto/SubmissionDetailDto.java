package uk.ac.swansea.autograder.api.lecturer.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDetailDto {
    private Long id;
    private Long submissionId;
    private String input;
    private String expectedOutput;
    private String actualOutput;
    private Boolean testCaseIsPassed;
}
