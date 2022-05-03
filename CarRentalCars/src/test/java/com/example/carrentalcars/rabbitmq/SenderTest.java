package com.example.carrentalcars.rabbitmq;

import org.junit.jupiter.api.Test;

class SenderTest {

    @Test
    void sendHalloWorldTest(){
        Sender sender=new Sender();
        sender.sendHalloWorld();
    }

}