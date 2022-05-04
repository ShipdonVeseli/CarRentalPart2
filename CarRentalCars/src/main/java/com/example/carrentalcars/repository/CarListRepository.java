package com.example.carrentalcars.repository;

import com.example.carrentalcars.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarListRepository extends MongoRepository<List<Car>, Long> {
    @Query(value="{userid:0}")
    List<Car> getAvailableCars();


//    @Query()
//    List<Car> getByPricesOfAvailableCars();
//
//
//    @Query( fields = "{'dayPrice':1,'id':0}")
//    List<Car> getByPricesOfAllCars();
}
