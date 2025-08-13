package uk.ac.swansea.autograder.api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "submissions")
@Data
public class Submission {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId;
    private String language;
    private String version;
    private String filename;
    private String code;
    private Status status;
    private String output;
    private Integer grade;
    private Long userId;

    private Integer totalTestCases;
    private Integer processedTestCases;
    private Integer correctTestCases;

    public enum Status {NEW, COMPILE_ERROR, PROCESSING, WRONG_ANSWER, ACCEPTED}
}