package com.example.carrentalcars.repository;

import com.example.carrentalcars.entity.Car;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, Long> {
    @Query()
    List<Car> getAvailableCars();

   // @Query("SELECT dayPrice FROM Car WHERE user.id IS NULL")
   @Query()
    List<Double> getPricesOfAvailableCars();

   // @Query("SELECT dayPrice FROM Car")
   @Query( fields = "{'dayPrice':1}")
    List<Double> getPricesOfAllCars();
}
