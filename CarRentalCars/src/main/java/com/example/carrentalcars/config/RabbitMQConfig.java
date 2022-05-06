package com.example.carrentalcars.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String REMOVECAR_QUEUE = "removeCar.queue";
    private static final String ADDCAR_QUEUE = "addCar.queue";

    private static final String REMOVECAR_ROUTINGKEY = "removeCar.routingKey";
    private static final String ADDCAR_ROUTINGKEY = "addCar.routingKey";

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    Queue queue() {
        return new Queue(REMOVECAR_QUEUE, true);
    }

    @Bean
    Queue addCarQueue() {
        return new Queue(ADDCAR_QUEUE, true);
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Binding removeCarBinding() {
        return BindingBuilder
                .bind(queue())
                .to(myExchange())
                .with(REMOVECAR_ROUTINGKEY)
                .noargs();
    }

    @Bean
    Binding addCarBinding() {
        return BindingBuilder
                .bind(addCarQueue())
                .to(myExchange())
                .with(ADDCAR_ROUTINGKEY)
                .noargs();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
