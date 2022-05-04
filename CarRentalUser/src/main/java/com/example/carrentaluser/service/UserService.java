package com.example.carrentaluser.service;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.entity.User;
import com.example.carrentaluser.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService{
    private RabbitTemplate rabbitTemplate;
    private UserRepository userRepository;

    @Autowired
    public UserService(RabbitTemplate rabbitTemplate, UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void sendMessage(Long userId) {
        rabbitTemplate.convertAndSend(exchange,routingkey, userId);
    }

    public User createNewUser(User user){
        //if(userRepository.findByUsername(user.getUsername()) == null){
            userRepository.save(user);
            return user;
        //}
    }

    public User getUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }
}
