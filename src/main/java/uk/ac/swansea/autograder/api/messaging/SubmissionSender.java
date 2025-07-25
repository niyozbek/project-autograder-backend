package uk.ac.swansea.autograder.api.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubmissionSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionSender.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void send(Long submissionId) {
        LOGGER.info("Sending submissionId=#{}", submissionId);
        stringRedisTemplate.convertAndSend("submissionId", String.valueOf(submissionId));
    }
}
