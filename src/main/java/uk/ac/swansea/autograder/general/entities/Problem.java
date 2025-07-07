package uk.ac.swansea.autograder.general.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "problems")
@Data
public class Problem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Status status;
    private Long lecturerId;

    public enum Status {ACTIVE, EXPIRED, ARCHIVED}
}