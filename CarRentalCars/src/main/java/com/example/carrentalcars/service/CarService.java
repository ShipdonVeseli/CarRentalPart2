package com.example.carrentalcars.service;

import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.repository.CarListRepository;
import com.example.carrentalcars.repository.CarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private CarRepository carRepository;
    private CarListRepository carListRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    public CarService(CarRepository carRepository,CarListRepository carListRepository) {
        this.carRepository = carRepository;
        this.carListRepository=carListRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(Long message) {
        logger.info("Received with userid: {}", message);
    }

    public Car createNewCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Car getCar(Long id) {
        Optional<Car> car = carRepository.findById(id);
        return car.get();
    }

    public List<Car> getAvailableCars() {
        return carListRepository.getAvailableCars();

    }

}
