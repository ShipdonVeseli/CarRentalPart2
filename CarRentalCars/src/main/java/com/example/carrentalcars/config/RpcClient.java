package com.example.carrentalcars.config;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class RpcClient {


    @Value("${spring.rabbitmq.routingkey}")
    private String ROUTING_KEY
            ;
    @Value("${spring.rabbitmq2.queue}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public void send(int n) {
        Long response = (Long) rabbitTemplate.convertSendAndReceive(directExchange.getName(), ROUTING_KEY, n);

        System.out.println("Got " + response + "");
    }
}
