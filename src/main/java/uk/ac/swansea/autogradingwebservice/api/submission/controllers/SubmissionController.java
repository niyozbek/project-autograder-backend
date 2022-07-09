package uk.ac.swansea.autogradingwebservice.api.submission.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.swansea.autogradingwebservice.api.submission.controllers.dto.RuntimeDto;
import uk.ac.swansea.autogradingwebservice.api.submission.controllers.dto.SubmissionDto;
import uk.ac.swansea.autogradingwebservice.api.submission.controllers.dto.SubmissionResultDto;
import uk.ac.swansea.autogradingwebservice.api.submission.services.SubmissionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    /**
     * Execute code and validate output
     * List of languages and versions is available from the route below
     *
     * @param dto
     * @return
     */
    @PostMapping("submit")
    public SubmissionResultDto submit(@Valid @RequestBody SubmissionDto dto) {
        return submissionService.submit(dto);
    }

    /**
     * Get list of available runtimes
     *
     * @return
     */
    @GetMapping("runtimes")
    public List<RuntimeDto> getRuntimes() {
        return submissionService.getRuntimes();
    }
}
