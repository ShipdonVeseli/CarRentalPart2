package com.example.carrentalcars.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMqRpcConfig {
    @Value("${spring.rabbitmq2.routingkey}")
    private String ROUTING_KEY
            ;
    @Value("${spring.rabbitmq2.queue}")
    private String queueName;

    @Value("${spring.rabbitmq2.exchange}")
    private String exchange;

    @Bean
    public Queue queue2() {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding2(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public RpcClient client() {
        return new RpcClient();
    }
}
