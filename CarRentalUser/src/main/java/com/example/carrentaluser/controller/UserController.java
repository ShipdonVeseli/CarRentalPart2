package com.example.carrentaluser.controller;

import com.example.carrentaluser.entity.User;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Value("${app.message}")
    private String response;

    @PostMapping("/produce")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
        userService.sendMessage(user.getId());
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
    public ResponseEntity<?> authenticate() {

        return null;
    }


    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<?> getCars(@PathVariable final Long userId, @RequestParam(name = "currency") String currency) {
        return null;
    }

    @PostMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> addCarToUser(@PathVariable final Long userId, @PathVariable final Long carId) {
        return null;
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> removeCarFromUser(@PathVariable final Long userId, @PathVariable final Long carId) {
        return null;
    }

}
