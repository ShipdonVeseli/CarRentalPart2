package com.example.carrentaluser.controller;

import com.example.carrentaluser.entity.User;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/produce")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
//        userService.sendMessage(user.getId());
        logger.info("Gesendet: " + user.getId());
        String response = (String) rabbitTemplate.convertSendAndReceive("user.exchange", "user.routingkey", "ich habe das gesendet");
        logger.info("Empfangen: " + response);
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
        try {
            userService.addCarToUser(userId + "," + carId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> removeCarFromUser(@PathVariable final Long userId, @PathVariable final String carId) {
        try {
            userService.removeCarFromUser(userId + "," + carId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
