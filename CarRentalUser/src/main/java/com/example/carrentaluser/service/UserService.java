package com.example.carrentaluser.service;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService{
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UserService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void sendMessage(Long userId) {
        rabbitTemplate.convertAndSend(exchange,routingkey, userId);
    }

}
