package com.example.carrentalcars.controller;

import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.service.CarService;
import com.example.carrentalcars.exception.*;
import com.rabbitmq.client.RpcClient;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;



    @Value("${spring.rabbitmq.routingkey}")
    private String ROUTING_KEY;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> createNewCar(@RequestBody Car newCar) {
        Car carEntity = carService.createNewCar(newCar);
        System.out.println(carEntity.getId());
        return new ResponseEntity<>(carEntity, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCars(@RequestParam(name = "currency") String currency) {
        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable("id") String id) {
        try {
            Car car = carService.getCar(id);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (CarDoesNotExistsException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<?> getCars(@PathVariable final String userId, @RequestParam(name = "currency") String currency) {
        List<Car> availableCars = carService.getCarsByUserId(userId);
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }

    @GetMapping("/availableCars")
    public ResponseEntity<?> getAvailableCars(@RequestParam(name = "currency") String currency) {
        List<Car> availableCars = carService.getCarsByUserId("0");
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }


    @GetMapping("/send")
    public ResponseEntity<?> send() {
        return new ResponseEntity<>(send(1), HttpStatus.OK);
    }

    public long send(int n) {
        Long response = (Long) rabbitTemplate.convertSendAndReceive(directExchange.getName(), ROUTING_KEY, n);

        System.out.println("Got " + response + "");
        return response;
    }


}
