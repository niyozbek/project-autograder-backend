package uk.ac.swansea.autograder.api.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.swansea.autograder.general.services.SubmissionMainService;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

public class SubmissionReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionReceiver.class);

    @Autowired
    private SubmissionMainService submissionMainService;

    public void receiveMessage(String submissionId) throws ResourceNotFoundException {
        LOGGER.info("Received submissionId=#{}", submissionId);
        submissionMainService.runSubmission(Long.valueOf(submissionId));
    }
}
