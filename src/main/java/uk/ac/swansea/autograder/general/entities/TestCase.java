package uk.ac.swansea.autograder.general.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "test_cases")
@Data
public class TestCase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId;
    private String input;
    private String expectedOutput;
}