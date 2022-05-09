package com.example.carrentalcars;

import com.example.carrentalcars.controller.CarController;
import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.UUID;


@SpringBootTest
class CarRentalCarsApplicationTests {

    @Autowired
    CarRepository carRepository;


    @Test
    public void testLocking(){
        String id = UUID.randomUUID().toString();
        Car car = new Car(id, 4,"manual",4.0, "0");
        car = carRepository.save(car);
        car.setDayPrice(5.0);

        Car samecar = carRepository.findById(car.getId());
        samecar.setDayPrice(40.0);
        carRepository.save(samecar);

        carRepository.save(car);

    }

}
