package com.example.carrentaluser.repository;

import com.example.carrentaluser.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, Long> {
    @Query(value="{username: '?0', password: '?1'}")
    User findByUsernameAndPassword(String username, String password);

    @Query(value="{username: '?0'}")
    User findByUsername(String username);
}
