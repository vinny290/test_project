package com.example.test_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "test.exchange";
    public static final String ROUTING_KEY = "test.created";
    public static final String QUEUE = "test.queue";

    @Bean
    public Queue testQueue() {
        return new Queue(QUEUE, false);
    }

    @Bean
    public TopicExchange testExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding testBinding(Queue testQueue, TopicExchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}

