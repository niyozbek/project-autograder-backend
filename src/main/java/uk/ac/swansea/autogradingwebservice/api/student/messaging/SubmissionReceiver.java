package uk.ac.swansea.autogradingwebservice.api.student.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.swansea.autogradingwebservice.api.student.services.SubmissionService;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

public class SubmissionReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionReceiver.class);

    @Autowired
    private SubmissionService submissionService;

    public void receiveMessage(String message) throws ResourceNotFoundException {
        LOGGER.info("Received <" + message + ">");
        submissionService.runSubmission(Long.valueOf(message));
    }
}
