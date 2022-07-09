package uk.ac.swansea.autogradingwebservice.api.submission.controllers.dto;

import lombok.Data;

@Data
public class RuntimeDto {
    private String language;
    private String version;
}
