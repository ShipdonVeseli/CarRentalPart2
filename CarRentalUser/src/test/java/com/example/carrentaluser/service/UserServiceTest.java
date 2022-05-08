package com.example.carrentaluser.service;

import com.example.carrentaluser.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void testGetUser(){
        String userName="Idiot";
        String userID= UUID.randomUUID().toString();

        String pwd="123";

        User user=new User();

        user.setUsername(userName);
        user.setId(userID);
        user.setPassword(pwd);

        try{




            userService.createNewUser(user);

        }catch (Exception e){
        }


        try {
        User result=userService.getUser(userName);
        assertEquals(user,result);





        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }





}