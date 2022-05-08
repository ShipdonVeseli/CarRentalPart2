package com.example.carrentalcars.service;

import com.example.carrentalcars.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sound.sampled.LineUnavailableException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CarServiceTest {

    @Autowired
    CarService carService;

    @Test
    void testGetCars(){
        try {
            String currency="USD";
            List<Car> result;

            result=carService.getAllCars(currency);

            System.out.println(result.toString());
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }


}