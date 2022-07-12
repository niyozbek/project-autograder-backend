package uk.ac.swansea.autogradingwebservice.api.student.services.dto;

import lombok.Data;

@Data
public class RuntimeDto {
    private String language;
    private String version;
}
