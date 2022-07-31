package uk.ac.swansea.autogradingwebservice.api.student.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import uk.ac.swansea.autogradingwebservice.api.student.entities.SubmissionTestResult;
import uk.ac.swansea.autogradingwebservice.api.student.services.SubmissionTestResultService;

@Controller
public class WebSocketController {
    @Autowired
    private SubmissionTestResultService submissionTestResultService;

    @MessageMapping("test-result")
    @SendTo("/topic/test-results")
    public SubmissionTestResult getTestResult(WebSocketMessage webSocketMessage)
            throws Exception {
//        Thread.sleep(1000);
        return submissionTestResultService.getSubmissionTestResult(webSocketMessage.getSubmissionId());
    }

}