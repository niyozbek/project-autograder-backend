package uk.ac.swansea.autogradingwebservice.api.lecturer.entities;

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
    // TODO: make status useful
    private Integer status;
    private Long lecturerId;
}