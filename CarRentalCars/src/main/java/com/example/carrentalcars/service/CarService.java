package com.example.carrentalcars.service;

import com.example.carrentalcars.entity.Car;

import com.example.carrentalcars.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    private CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(Long message) {
        logger.info("Received with userid: {}", message);
    }

    public Car createNewCar(Car car) {
        car.setId(UUID.randomUUID().toString());
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Car getCar(String id) { return carRepository.findById(id); }

    public List<Car> getCarsByUserId(String id) { return carRepository.findByUserid(id); }

}
