package uk.ac.swansea.autogradingwebservice.api.student.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * WebSocketMessage queues are ideal for requests which may take a long time to process.
 * Messaging to process submissions, enables asynchronously executing tests.
 * Direct/synchronous/blocking calls increase rest response time.
 * Also, Messaging helps to use the third party code execution engine synchronously, which
 * allows balancing a load on backend and api during peak hours
 */
@Configuration
public class MessagingConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("submissionId"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(SubmissionReceiver submissionReceiver) {
        return new MessageListenerAdapter(submissionReceiver, "receiveMessage");
    }

    @Bean
    SubmissionReceiver receiver() {
        return new SubmissionReceiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
