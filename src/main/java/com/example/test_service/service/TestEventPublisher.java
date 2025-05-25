package com.example.test_service.service;

import com.example.test_service.config.RabbitMQConfig;
import com.example.test_service.model.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TestEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTestCreatedEvent(Test test) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                test
        );
    }
}

