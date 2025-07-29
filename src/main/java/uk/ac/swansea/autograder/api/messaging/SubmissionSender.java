package uk.ac.swansea.autograder.api.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubmissionSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionSender.class);

    private final StringRedisTemplate stringRedisTemplate;

    public SubmissionSender(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void send(Long submissionId) {
        LOGGER.info("Sending submissionId=#{}", submissionId);
        stringRedisTemplate.convertAndSend("submissionId", String.valueOf(submissionId));
    }
}
