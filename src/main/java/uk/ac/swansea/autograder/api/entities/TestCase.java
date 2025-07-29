package uk.ac.swansea.autograder.api.entities;

import lombok.Data;

import jakarta.persistence.*;

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