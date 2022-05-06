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

    @RabbitListener(queues = "removeCar.queue")
    public String removeCarFromUser(String message) {
        logger.info("Received with userid: {}", message);
        String[] parts = message.split(",");
        Car updatedcar = carRepository.findById(parts[1]);
        if(updatedcar.getUserid().equals(parts[0])) {
            updatedcar.setUserid("0");
            carRepository.save(updatedcar);
            return "successful";
        }
        return "";
    }

    @RabbitListener(queues = "addCar.queue")
    public String addCarToUser(String message) {
        logger.info("Received with userid: {}", message);
        String[] parts = message.split(",");
        Car updatedcar = carRepository.findById(parts[1]);
        if(updatedcar.getUserid().equals("0")) {
            updatedcar.setUserid(parts[0]);
            carRepository.save(updatedcar);
            return "successful";
        }
        return "";
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
