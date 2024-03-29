package com.example.carrentaluser.repository;

import com.example.carrentaluser.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    @Query(value="{username: '?0', password: '?1'}")
    User findByUsernameAndPassword(String username, String password);

    @Query(value="{username: '?0'}")
    User findByUsername(String username);

    @Query(value="{id: '?0'}")
    User findById(String id);
}
