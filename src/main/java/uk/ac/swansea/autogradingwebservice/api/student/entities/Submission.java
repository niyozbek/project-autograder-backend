package uk.ac.swansea.autogradingwebservice.api.student.entities;

import lombok.Data;

import javax.persistence.*;

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
    private Long studentId;

    public enum Status {NEW, COMPILE_ERROR, WRONG_ANSWER, ACCEPTED}
}