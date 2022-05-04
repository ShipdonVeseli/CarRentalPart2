package com.example.carrentalcars.repository;

import com.example.carrentalcars.entity.Car;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, Long> {
    //@Query("FROM Car WHERE user.id IS NULL")
    List<Car> getAvailableCars();

   // @Query("SELECT dayPrice FROM Car WHERE user.id IS NULL")
    List<Double> getPricesOfAvailableCars();

   // @Query("SELECT dayPrice FROM Car")
    List<Double> getPricesOfAllCars();
}
