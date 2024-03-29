package com.example.carrentalcars.repository;

import com.example.carrentalcars.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, Long> {
    @Query(value="{'userid': '?0'}")
    List<Car> findByUserid(String id);

    @Query(value="{'id': '?0'}")
    Car findById(String id);
}
