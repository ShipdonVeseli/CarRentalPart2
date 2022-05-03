package com.example.carrentaluser.rabbitmq;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Reciver {

    public void receiveSimpleMessage(){
        try {
            String host = "localhost";
            String queueName="test queue";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection=factory.newConnection();
            Channel channel=connection.createChannel();

            channel.queueDeclare(queueName,false,false,false,null);

            DeliverCallback deliverCallback=((consumerTag, message) -> {
                String receivedMessage=new String(message.getBody(), "UTF-8");
                System.out.println("received Message: "+receivedMessage);
            });
            channel.basicConsume(queueName,true,deliverCallback,consumerTag -> {});


        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
