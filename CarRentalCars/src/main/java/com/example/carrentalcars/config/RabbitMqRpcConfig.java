package com.example.carrentalcars.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
    Exchange myExchange2() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(myExchange2())
                .with(ROUTING_KEY)
                .noargs();
    }

    @Bean
    public RpcClient rpcClient() {
        return new RpcClient();
    }
}
