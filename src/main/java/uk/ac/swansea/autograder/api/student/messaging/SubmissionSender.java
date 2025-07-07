package uk.ac.swansea.autograder.api.student.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.AutograderApplication;

@Service
public class SubmissionSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutograderApplication.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void send(Long submissionId) {
        LOGGER.info("Sending submissionId=#{}", submissionId);
        stringRedisTemplate.convertAndSend("submissionId", String.valueOf(submissionId));
    }
}
