package uk.ac.swansea.autogradingwebservice.api.student.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.AutogradingWebServiceApplication;

@Service
public class SubmissionSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutogradingWebServiceApplication.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void send(Long submissionId) {
        LOGGER.info("Sending message...");
        stringRedisTemplate.convertAndSend("submissionId", String.valueOf(submissionId));
    }
}
