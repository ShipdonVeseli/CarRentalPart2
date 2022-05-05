package com.example.carrentaluser.controller;

import com.example.carrentaluser.config.RabbitMQConfig;
import com.example.carrentaluser.entity.User;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class UserController {
    private UserService userService;
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${app.message}")
    private String response;

    @GetMapping("/send")
    public String send(String message) {
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        logger.info("client send：{}", newMessage);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID() + "");
        Object result = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_QUEUE1, newMessage, correlationData);

        String response = "";
        if (result != null) {
            String correlationId = newMessage.getMessageProperties().getCorrelationId();
            logger.info("correlationId:{}", correlationId);

//            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();

//            String msgId = (String) headers.get("spring_returned_message_correlation");

//            if (msgId.equals(correlationId)) {
//                response = new String(result.getBody());
//                logger.info("client receive：{}", response);
//            }
        }
        return "";
//        return response;
    }

    @PostMapping("/produce")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
        //userService.sendMessage(user.getId());
        logger.info("user sent: " + user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        try {
            User userEntity = userService.createNewUser(newUser);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody User user) {
        try {
            User userEntity = userService.getUser(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(userEntity, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> addCarToUser(@PathVariable final Long userId, @PathVariable final String carId) {
        userService.sendMessage("add,"+userId+","+carId);
        return null;
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> removeCarFromUser(@PathVariable final Long userId, @PathVariable final String carId) {
        userService.sendMessage("remove,"+userId+","+carId);
        return null;
    }

}
