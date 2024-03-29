package com.example.carrentaluser.controller;

import com.example.carrentaluser.entity.User;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
            return new ResponseEntity<>("Username doesn't exist. Please register first.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> addCarToUser(@PathVariable final String userId, @PathVariable final String carId, @RequestHeader(name = "Authorization") String userIdAuth) {
        if (userService.checkIfUserExists(userIdAuth)) {
            try {
                userService.addCarToUser(userId + "," + carId);
            } catch (UserDoesNotExistsException | IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not authenticated.", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<?> removeCarFromUser(@PathVariable final String userId, @PathVariable final String carId, @RequestHeader(name = "Authorization") String userIdAuth) {
        if (userService.checkIfUserExists(userIdAuth)) {
            try {
                userService.removeCarFromUser(userId + "," + carId);
            } catch (UserDoesNotExistsException | IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not authenticated.", HttpStatus.UNAUTHORIZED);
        }
    }

}
