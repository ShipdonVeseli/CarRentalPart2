package com.example.carrentaluser.rabbitmq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReciverTest {


    @Test
    void receiveSimpleMessageTest(){
        Reciver reciver=new Reciver();
        reciver.receiveSimpleMessage();


        Sender sender=new Sender();
        sender.sendHalloWorld();



    }

}