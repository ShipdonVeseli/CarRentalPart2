package com.example.carrentalcars.controller;

import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.service.CarService;
import com.example.carrentalcars.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createNewCar(@RequestBody Car newCar, @RequestParam(name = "userId") String userId) {
        if(carService.checkIfUserExists(userId).equals("true")) {
            Car carEntity = carService.createNewCar(newCar);
            System.out.println(carEntity.getId());
            return new ResponseEntity<>(carEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(@RequestParam(name = "currency") String currency, @RequestParam(name = "userId") String userId) {
        if(carService.checkIfUserExists(userId).equals("true")) {
            List<Car> cars = carService.getAllCars(currency);
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCar(@PathVariable("id") String id, @RequestParam(name = "userId") String userId) {
        if(carService.checkIfUserExists(userId).equals("true")) {
            try {
                Car car = carService.getCar(id);
                return new ResponseEntity<>(car, HttpStatus.OK);
            } catch (CarDoesNotExistsException e) {
                return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("users/{userId}/cars")
    public ResponseEntity<?> getCars(@PathVariable final String userId, @RequestParam(name = "currency") String currency) {
        if(carService.checkIfUserExists(userId).equals("true")) {
            List<Car> availableCars = carService.getCarsByUserId(userId, currency);
            return new ResponseEntity<>(availableCars, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/cars/availableCars")
    public ResponseEntity<?> getAvailableCars(@RequestParam(name = "currency") String currency, @RequestParam(name = "userId") String userId) {
        if(carService.checkIfUserExists(userId).equals("true")) {
            List<Car> availableCars = carService.getCarsByUserId("0", currency);
            return new ResponseEntity<>(availableCars, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
