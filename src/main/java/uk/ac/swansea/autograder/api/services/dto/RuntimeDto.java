package uk.ac.swansea.autograder.api.services.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RuntimeDto implements Serializable {
    private String language;
    private String version;
}
