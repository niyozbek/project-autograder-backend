package uk.ac.swansea.autogradingwebservice.api.student.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "submission_test_results")
@Data
public class SubmissionTestResult {

    @Id
    @Column(name = "submissionId")
    private Long submissionId;
    private Status status;

    private Integer totalTestCases;
    private Integer processedTestCases;
    private Integer correctTestCases;
    public enum Status {PROCESSING, COMPLETED}
}