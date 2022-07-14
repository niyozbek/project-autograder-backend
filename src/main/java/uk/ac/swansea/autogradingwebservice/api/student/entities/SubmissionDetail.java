package uk.ac.swansea.autogradingwebservice.api.student.entities;

import lombok.Data;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;

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
    // it is okay to use eager, since there won't be many test cases per submission
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="test_case_id")
    private TestCase testCase;
    private String actualOutput;
    private Boolean testCaseIsPassed;
}
