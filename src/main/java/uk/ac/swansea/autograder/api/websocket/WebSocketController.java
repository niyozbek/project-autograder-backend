package uk.ac.swansea.autograder.api.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import uk.ac.swansea.autograder.api.entities.Submission;
import uk.ac.swansea.autograder.api.services.SubmissionService;

@Controller
public class WebSocketController {
    private final SubmissionService submissionService;

    public WebSocketController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @MessageMapping("test-result")
    @SendTo("/topic/test-results")
    public Submission getTestResult(WebSocketMessage webSocketMessage)
            throws Exception {
//        Thread.sleep(1000);
        return submissionService.getSubmission(webSocketMessage.getSubmissionId());
    }

}