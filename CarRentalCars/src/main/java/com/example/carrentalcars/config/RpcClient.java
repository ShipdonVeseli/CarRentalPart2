package com.example.carrentalcars.config;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RpcClient {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public void send(int n) {
        Long response = (Long) rabbitTemplate.convertSendAndReceive(directExchange.getName(), RabbitMqRpcConfig.ROUTING_KEY, n);

        System.out.println("Got " + response + "");
    }
}
