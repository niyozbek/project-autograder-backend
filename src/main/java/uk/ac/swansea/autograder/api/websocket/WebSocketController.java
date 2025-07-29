package uk.ac.swansea.autograder.api.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import uk.ac.swansea.autograder.api.entities.SubmissionTestResult;
import uk.ac.swansea.autograder.api.services.SubmissionTestResultService;

@Controller
public class WebSocketController {
    private final SubmissionTestResultService submissionTestResultService;

    public WebSocketController(SubmissionTestResultService submissionTestResultService) {
        this.submissionTestResultService = submissionTestResultService;
    }

    @MessageMapping("test-result")
    @SendTo("/topic/test-results")
    public SubmissionTestResult getTestResult(WebSocketMessage webSocketMessage)
            throws Exception {
//        Thread.sleep(1000);
        return submissionTestResultService.getSubmissionTestResult(webSocketMessage.getSubmissionId());
    }

}