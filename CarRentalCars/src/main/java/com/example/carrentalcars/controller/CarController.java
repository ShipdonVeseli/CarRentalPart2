package com.example.carrentalcars.controller;

import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.service.CarService;
import com.example.carrentalcars.exception.*;
import com.example.carrentalcars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> createNewCar(@RequestBody Car newCar) {
        Car carEntity = carService.createNewCar(newCar);
        return new ResponseEntity<>(carEntity, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCars(@RequestParam(name = "currency") String currency) {

        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable("id") Long id) {
        try {
            Car car = carService.getCar(id);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (CarDoesNotExistsException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/availableCars")
    public ResponseEntity<?> getAvailableCars(@RequestParam(name = "currency") String currency) {

        List<Car> availableCars = carService.getAvailableCars();
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }

}
