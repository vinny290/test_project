package com.example.test_service.service;

import com.example.test_service.config.RabbitMQConfig;
import com.example.test_service.model.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleTestCreated(Test test) {
        System.out.println("Received Test Event: " + test.getTitle());
    }
}
