package uk.ac.swansea.autograder.api.entities;

import jakarta.persistence.*;
import lombok.Data;

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
    private Long userId;

    public enum Status {ACTIVE, EXPIRED, ARCHIVED}
}