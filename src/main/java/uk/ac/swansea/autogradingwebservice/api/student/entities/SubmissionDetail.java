package uk.ac.swansea.autogradingwebservice.api.student.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "submission_details")
@Data
public class SubmissionDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long submissionId;
    private Long testCaseId;
    private String actualOutput;
    private Boolean testCaseIsPassed;
}
