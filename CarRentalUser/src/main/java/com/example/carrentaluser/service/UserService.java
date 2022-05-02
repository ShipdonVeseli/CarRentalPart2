package com.example.carrentaluser.service;
import com.example.carrentaluser.exception.*;
import com.example.carrentaluser.repository.UserRepository;
import com.example.carrentaluser.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        /*if(user.isEmpty()) {
            throw new UserDoesNotExistsException(username);
        }*/
        return user.get();
    }

    public User getUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public void removeUser(User user){
        Optional<User> delUser=userRepository.findByUsername(user.getUsername());
        if(delUser.isPresent()) {
            userRepository.delete(delUser.get());
        }

    }



    public void deliteDB(){
        userRepository.deleteAll();
    }

    public boolean checkIfUserHasCar(Long userId, Long carId) {

        return false;
    }


}
